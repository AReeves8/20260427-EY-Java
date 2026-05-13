import java.util.concurrent.ArrayBlockingQueue;

public class Dryer implements Runnable {

    private ArrayBlockingQueue<String> folderBasket;    // dried clothes waiting to be folded
    private ArrayBlockingQueue<String> dryerBasket;     // contains clothes waiting for the dryer
    private String stopSignal;

    public Dryer(ArrayBlockingQueue<String> folderBasket, ArrayBlockingQueue<String> dryerBasket, String stopSignal) {
        this.folderBasket = folderBasket;
        this.dryerBasket = dryerBasket;
        this.stopSignal = stopSignal;
    }

    @Override
    public void run() {

        System.out.println("----- " + Thread.currentThread().getName() + " HAS BEGUN -----");

        try {

            while(true) {

                // .take() will wait for an element as opposed to .poll() that would return null if queue was empty
                String item = dryerBasket.take();

                if(item.equals(stopSignal)) {
                    folderBasket.put(item);
                    break;      // end the infinite loop when the dryer is done
                }

                System.out.println(Thread.currentThread().getName() + " HAS STARTED " + item);
                Thread.sleep(5000);     // simulate drying time

                folderBasket.put(item);
            }

            System.out.println("----- " + Thread.currentThread().getName() + " HAS FINISHED -----");

        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
