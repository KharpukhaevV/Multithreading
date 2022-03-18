import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableThreadPool implements ThreadPool{
    private BlockingQueue<Runnable> workerQueue;
    private ArrayList<Thread> workerThreads;
    private List<Runnable> list;
    private int min;
    private int max;

    public ScalableThreadPool(int min, int max) {
        this.min = min;
        this.max = max;
        workerQueue = new LinkedBlockingQueue<>();
        workerThreads = new ArrayList<>();
        list = new ArrayList<>();
        for (int i = 0; i < min; i++) {
            Thread t = new Worker("Custom_Pool_Thread_" + (i + 1), workerQueue);
            workerThreads.add(t);
        }
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
        list.add(runnable);
        if (list.size() > workerThreads.size() && workerThreads.size() < max) {
            for (int i = min; i <max ; i++) {
                Thread t = new Thread(new Worker("Custom_Pool_Thread_" + (i + 1), workerQueue));
                t.start();
                workerThreads.add(t);
            }
        }
        if (workerThreads.size() > min && list.size() < workerThreads.size() && list.size() >= min) {
            for (int i = workerThreads.size()-1; i >=list.size()-1; i--) {
                list.remove(runnable);
            }
        }
    }
}
