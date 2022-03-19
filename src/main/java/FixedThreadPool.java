import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class FixedThreadPool implements ThreadPool{
    private BlockingQueue<Runnable> workerQueue;
    private ArrayList<Thread> workerThreads;
    private int numThreads;
    private AtomicBoolean isShutDown;

    public FixedThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.isShutDown = new AtomicBoolean(false);
        workerQueue = new LinkedBlockingQueue<>();
        workerThreads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Worker("Custom_Pool_Thread_" + (i + 1), workerQueue, this);
            workerThreads.add(t);
        }
    }

    public AtomicBoolean getIsShutDown() {
        return isShutDown;
    }

    @Override
    public void start() {
        for (int i = 0; i < numThreads; i++) {
            workerThreads.get(i).start();
        }
    }

    @Override
    public void execute(Runnable runnable) throws InterruptedException {
        if (!isShutDown.get()) {
            workerQueue.put(runnable);
        } else {
            throw new InterruptedException("Thread Pool shutdown is initiated, unable to execute task");
        }
    }

    public void shutdown() {
        isShutDown = new AtomicBoolean(true);
    }
}
