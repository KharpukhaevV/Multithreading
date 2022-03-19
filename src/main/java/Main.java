public class Main {
    public static void main(String[] args) throws InterruptedException {
//        FixedThreadPool fixedThreadPool = new FixedThreadPool(5);
//        fixedThreadPool.start();
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            fixedThreadPool.execute(() -> System.out.println("Task - " + (finalI + 1) + ". Print from : " + Thread.currentThread().getName()));
//        }
//        fixedThreadPool.shutdown();


        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(3, 10);
        scalableThreadPool.start();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            scalableThreadPool.execute(() -> System.out.println("Task - " + (finalI + 1) + ". Print from : " + Thread.currentThread().getName()));
        }
        scalableThreadPool.shutdown();
    }
}
