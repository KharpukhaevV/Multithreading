import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScalableThreadPool implements ThreadPool{
    private BlockingQueue<Runnable> workerQueue;
    private ArrayList<Thread> workerThreads;
    private int min;
    private int max;
    private AtomicBoolean isShutDown;

    public ScalableThreadPool(int min, int max) {
        this.min = min;
        this.max = max;
        this.isShutDown = new AtomicBoolean(false);
        workerQueue = new LinkedBlockingQueue<>();
        workerThreads = new ArrayList<>();
        for (int i = 0; i < min; i++) {
            Thread t = new Worker("Custom_Pool_Thread_" + (i + 1), workerQueue, this);
            workerThreads.add(t);
        }
    }

    @Override
    public AtomicBoolean getIsShutDown() {
        return isShutDown;
    }

    @Override
    public void start() {
        for (int i = 0; i < min; i++) {
            workerThreads.get(i).start();
        }
    }

    @Override
    public void execute(Runnable runnable) throws InterruptedException {
        workerQueue.put(runnable);
        if(workerQueue.size() > workerThreads.size() && workerThreads.size() == min){
            Thread t = new Thread(new Worker("Custom_Pool_Thread_" + (workerThreads.size() + 1), workerQueue, this));
            t.setName("Custom_Pool_Thread_" + (workerThreads.size() + 1));
            workerThreads.add(t);
            t.start();
        }
        if (workerQueue.size() < workerThreads.size() && workerThreads.size() > min && workerQueue.size() >= min){
            for (int i = workerThreads.size()-1; i >=workerQueue.size()-1; i--) {
                workerThreads.remove(i);
            }
        }
    }

    public void shutdown() {
        isShutDown = new AtomicBoolean(true);
    }

}
