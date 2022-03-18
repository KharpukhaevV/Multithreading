import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool{
    private BlockingQueue<Runnable> workerQueue;
    private ArrayList<Thread> workerThreads;
    private int numThreads;

    public FixedThreadPool(int numThreads) {
        this.numThreads = numThreads;
        workerQueue = new LinkedBlockingQueue<>();
        workerThreads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Worker("Custom_Pool_Thread_" + (i + 1), workerQueue);
            workerThreads.add(t);
        }
    }

    @Override
    public void start() {
        for (int i = 0; i < numThreads; i++) {
            workerThreads.get(i).start();
        }
    }

    @Override
    public void execute(Runnable runnable) throws InterruptedException {
        workerQueue.put(runnable);
    }
}
