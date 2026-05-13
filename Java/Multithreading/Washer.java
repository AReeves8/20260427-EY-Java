import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * RUNNABLE
 *      - include a method that can be ran on a thread
 *      - functional interface and therefore a candidate to be an anonymous inner class
 * 
 */

public class Washer implements Runnable {

    private List<String> dirtyClothes;                  // dirty items waiting to be washed
    private ArrayBlockingQueue<String> dryerBasket;     // contains clothes waiting for the dryer
    private String stopSignal;

    public Washer(List<String> dirtyClothes, ArrayBlockingQueue<String> dryerBasket, String stopSignal) {
        this.dirtyClothes = dirtyClothes;
        this.dryerBasket = dryerBasket;
        this.stopSignal = stopSignal;
    }


    /**
     * method is called when a thread starts 
     * the thread will execute this method   
     */
    @Override
    public void run() {

        System.out.println("----- " + Thread.currentThread().getName() + " HAS BEGUN -----");

        try{
            for(String item : dirtyClothes) {
                if(!item.equals(stopSignal)) {

                    System.out.println(Thread.currentThread().getName() + " HAS STARTED " + item);

                    // simulate washing by having the thread sleep for 3 seconds per item
                    Thread.sleep(3000);     // puts thread into TIMED_WAITING  
                    
                    // add clothes to the dryer when they're done in the washer
                    // .put() will wait for space in the queue whereas .add() would not wait
                    dryerBasket.put(item);
                }
            }

            System.out.println("----- " + Thread.currentThread().getName() + " HAS FINISHED -----");
            dryerBasket.put(stopSignal);        // passing the stop signal along to the dryer so it knows when to stop

        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        
    }

}
