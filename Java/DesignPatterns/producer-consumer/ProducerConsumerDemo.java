import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsumerDemo {

    private static final String CSV_FILE = ".\\schedule.csv";
    private static final String REPORT_FILE = ".\\schedule-report.txt";

    /**
     * stores the data after it is read in and before it is consumed
     */
    static ArrayBlockingQueue<ScheduleEntry> entries = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {

        Thread producerThread = new Thread() {
            @Override
            public void run() {
                readInScheduleData();
            }
        };

        Thread consumerThread = new Thread() {
            @Override
            public void run() {
                createScheduleReport();
            }
        };

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }

    public static void readInScheduleData() {

        int lineCount = 0;

        InputStream inputStream = ProducerConsumerDemo.class.getClassLoader().getResourceAsStream(CSV_FILE);
        if(inputStream == null) {
            System.out.println("File: " + CSV_FILE + " could not be found.");
            return;
        }

        try(Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {

            // skips past the next newline charcater to get to the next line
            scanner.nextLine();         // skip the header row of the CSV

            // read until EOF
            while(scanner.hasNextLine()) {

                lineCount++;
                System.out.println("READING LINE: " + lineCount);
                
                String line = scanner.nextLine().trim();
                String[] values = line.split(",");
                LocalDate date = null;
                LocalTime startTime = null;
                LocalTime endTime = null;

                String className = values[0].trim();

                // only set the values if not at EOF
                if(!className.equals("---EOF--")) {
                    date = LocalDate.parse(values[1].trim());
                    startTime = LocalTime.parse(values[2].trim());
                    endTime = LocalTime.parse(values[3].trim());
                }

                // taking the data in the file and turning it into Java objects that can be worked with
                entries.put(new ScheduleEntry(className, date, startTime, endTime));

            }
        } catch(InterruptedException e) {
            System.out.println("ERROR ADDING DATA TO QUEUE.");
        }
    }


    public static void createScheduleReport() {

        int lineCount = 0;
        DateTimeFormatter displayDate = DateTimeFormatter.ofPattern("EEE, MMM d yyyy");
        DateTimeFormatter displayTime = DateTimeFormatter.ofPattern("h:mm a");
        DateTimeFormatter displayDateTime = DateTimeFormatter.ofPattern("EEE, MMM d yyyy 'at' h:mm a");
        LocalDate today = LocalDate.now();      // today's date


        try(PrintWriter output = new PrintWriter(new FileOutputStream(REPORT_FILE))) {

            output.println("=".repeat(85));
            output.println("=".repeat(30) + " TRAINING CLASS SCHEDULE " + "=".repeat(30));
            output.println("=".repeat(85));
            output.println("Report Generated: " + LocalDateTime.now().format(displayDateTime));
            output.println();
            output.printf("%-26s %-20s %-12s %-12s %s%n", "Session", "Date", "Start Time", "Duration", "Days Away");
            output.println("-".repeat(85));


            Duration totalTime = Duration.ZERO;

            while(true) {

                lineCount++;
                System.out.println("WRITING LINE: " + lineCount);

                ScheduleEntry entry = entries.take();

                if(entry.getName().equals("---EOF--")) {
                    break;
                }

                Duration sessionLength = entry.getDuration();
                totalTime = totalTime.plus(sessionLength);

                long daysAway = ChronoUnit.DAYS.between(today, entry.getDate());

                output.printf("%-26s %-20s %-12s %-12s %s%n", 
                    entry.getName(), 
                    entry.getDate().format(displayDate), 
                    entry.getStartTime().format(displayTime),
                    sessionLength.toHours(), 
                    daysAway
                );
                output.println("-".repeat(85));
            }
            output.println("Total Training Time: " + totalTime.toHours());

        } catch (FileNotFoundException e) {
            System.out.println("Could not find report file: " + REPORT_FILE);
            e.printStackTrace();
        } catch(InterruptedException e) {
            System.out.println("ERROR READING IN DATA FROM QUEUE.");
        }


    }
}
