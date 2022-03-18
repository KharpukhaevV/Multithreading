import java.util.concurrent.BlockingQueue;

public class Worker extends Thread{
    private final BlockingQueue<Runnable> workQueue;

    public Worker(String name, BlockingQueue<Runnable> workQueue) {
        super(name);
        this.workQueue = workQueue;
    }

    public void run() {
        while (workQueue.isEmpty()) {
//            Runnable r;
//            while ((r = workQueue.poll()) != null) {
//                r.run();
//            }
            try {
                workQueue.take().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
