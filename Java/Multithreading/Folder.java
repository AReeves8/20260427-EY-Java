import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Folder implements Runnable {

    private List<String> foldedClothes;                 // items that have been folded
    private ArrayBlockingQueue<String> folderBasket;    // contains clothes waiting for the folder
    private String stopSignal;

    public Folder(List<String> foldedClothes, ArrayBlockingQueue<String> folderBasket, String stopSignal) {
        this.foldedClothes = foldedClothes;
        this.folderBasket = folderBasket;
        this.stopSignal = stopSignal;
    }

    @Override
    public void run() {

        System.out.println("----- " + Thread.currentThread().getName() + " HAS BEGUN -----");

        try {

            while(true) {

                // .take() will wait for an element as opposed to .poll() that would return null if queue was empty
                String item = folderBasket.take();

                if(item.equals(stopSignal)) {
                    break;      // end the infinite loop when the dryer is done
                }

                System.out.println(Thread.currentThread().getName() + " HAS STARTED " + item);
                Thread.sleep(2000);     // simulate folding time

                foldedClothes.add(item);
            }

            System.out.println("----- " + Thread.currentThread().getName() + " HAS FINISHED -----");

        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
