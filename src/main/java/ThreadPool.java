import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public interface ThreadPool {
    void start();
    void execute(Runnable runnable) throws InterruptedException;

    AtomicBoolean getIsShutDown();
}
