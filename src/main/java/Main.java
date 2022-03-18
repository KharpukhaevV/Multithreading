public class Main {
    public static void main(String[] args) throws InterruptedException {
//        FixedThreadPool fixedThreadPool = new FixedThreadPool(10);
//        fixedThreadPool.start();
//        for (int i = 0; i < 5; i++) {
//            fixedThreadPool.execute(() -> System.out.println("Print from : " + Thread.currentThread().getName()));
//        }

        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(2, 10);
        scalableThreadPool.start();
        for (int i = 0; i < 100; i++) {
            scalableThreadPool.execute(() -> System.out.println("Print from : " + Thread.currentThread().getName()));
        }
    }
}
