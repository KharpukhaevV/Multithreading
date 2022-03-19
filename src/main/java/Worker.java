import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker extends Thread{
    private final BlockingQueue<Runnable> workQueue;
    private ThreadPool threadPool;

    public Worker(String name, BlockingQueue<Runnable> workQueue, ThreadPool threadPool) {
        super(name);
        this.workQueue = workQueue;
        this.threadPool = threadPool;
    }

    public void run() {
        while (!threadPool.getIsShutDown().get() || !workQueue.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                workQueue.take().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
