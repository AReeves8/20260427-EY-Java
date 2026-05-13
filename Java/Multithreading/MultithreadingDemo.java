import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MultithreadingDemo {


    /**
     * MULTITHREADING
     *      - run multiple processes at the same time (concurrently)
     * 
     *      THREADS
     *          - every thread has its own stack and program counter (tracks which instruction set it is on)
     *          - every thread shares the same heap
     *              - static variables, and instance fields are all shared
     * 
     *      THREAD STATES
     *          - NEW - thread has been created, but not started
     *          - RUNNABLE - thread has started but is waiting for CPU resources
     *          - BLOCKED - waiting to aquire a lock
     *          - WAITING - the thread called wait() -> stay until notify() is called
     *          - TIMED_WAITING - waiting for a specific amount of time; sleep()
     *          - TERMINATED - thread has completed -> run() finished or threw an exception
     * 
     */

    public static void main(String[] args) {

        // Can create threads as Anonymous Inner Classes
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("CODE IS RUNNING ON A THREAD!!!!! WOO!!");
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("CODE IS RUNNING ON A THREAD!!!!! WOO!!");
            }
        };
        Thread thread2 = new Thread(runnable);



        /**
         * DOING LAUNDRY
         *      washer -> dryer -> fold 
         *          - you normally will wash one load while another is drying -> multithreading
         */

        /**
         * CONCURRENT COLLECTIONS
         *      - java.util.concurrent
         *      - collections that are designed for thread safety
         *          - auto synchronized across threads
         *          - only allows one thread access at a time
         * 
         *      ARRAY BLOCKING QUEUE
         *          - really ideal when you don't need shared data to live a long time in the collection
         *          - have to take items off the queue to manipulate them
         *              - great for transaction management
         *                  - when multiple operations need to be completed as one unit of work
         *                  - all or none - either all operations succeed, or we end the transaction
         * 
         *      COLLECTIONS.SYNCHRONIZED
         *          - returns a thread safe version of a collection
         *          - better when you want data to persist longer in memory
         * 
         */
        ArrayBlockingQueue<String> dryingbasket = new ArrayBlockingQueue<>(3);
        ArrayBlockingQueue<String> foldingbasket = new ArrayBlockingQueue<>(3);

        List<String> dirtyClothes = List.of("T-Shirt", "Hoodie", "Socks", "Jeans", "Sweater");
        List<String> cleanedClothes = Collections.synchronizedList(new LinkedList<>());
        final String STOP_SIGNAL = "### STOP ###";

        // these threads are now in NEW state
        Thread washerThread = new Thread(new Washer(dirtyClothes, dryingbasket, STOP_SIGNAL), "WASHER");
        Thread dryerThread = new Thread(new Dryer(foldingbasket, dryingbasket, STOP_SIGNAL), "DRYER");
        Thread folderThread = new Thread(new Folder(cleanedClothes, foldingbasket, STOP_SIGNAL), "FOLDER");

        // starting threads -> puts them in RUNNABLE state
        washerThread.start();
        dryerThread.start();
        folderThread.start();

        // put the main execution thread into the waiting state until these other threads have finished
        // don't want the main execution thread ending before the individual threads have finished
        // puts main into the WAITING state
        try {
            washerThread.join();
            dryerThread.join();
            folderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
