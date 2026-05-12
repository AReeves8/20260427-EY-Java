import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FilesAndDates {

    private static final String CSV_FILE = ".\\schedule.csv";
    private static final String REPORT_FILE = ".\\schedule-report.txt";


    public static void main(String[] args) {

        /**
         * FILE IO
         *      InputStream/OutputStream
         *          - FileInputStream/FileOutputStream
         *          - read in raw bytes
         * 
         *      Reader/Writer
         *          - InputStreamReader/OutputStreamWriter
         *          - BufferedReader/BufferedWriter
         *          - read in characters
         * 
         *      Scanner/PrintWriter
         *          - convenience tool
         *          - wrap around some Stream
         */
        tryWithoutResourcesExample();
        tryWithResourcesExample();
        

        

    }

    public static String[] parseCSV(String text) {
        // split up the given text per newline
        return text.split("\\n");
    }

    public static void tryWithoutResourcesExample() {
        /**
         * could create a FileInputStream directly that points to file location
         *      - throws FileNotFoundException that needs to be handles
         */
        //InputStream inputStream = new FileInputStream(CSV_FILE);   
        
        /**
         * CLASS LOADER & GET RESOURCE AS STREAM
         *      - returns some stream for the given resource
         *      - returns null if the file is not found
         */
        InputStream inputStream = FilesAndDates.class.getClassLoader().getResourceAsStream(CSV_FILE);   // file is opened

        if(inputStream == null) {
            System.out.println("File: " + CSV_FILE + " could not be found.");
            return;
        }

        try{
            byte[] fileBytes = inputStream.readAllBytes();
            System.out.println("RAW FILE BYTES: ");
            System.out.println(fileBytes.toString());

            // decode bytes into characters
            String fileText = new String(fileBytes, StandardCharsets.UTF_8);

            // parsing CSV
            String[] fileLines = parseCSV(fileText);

            // format/manipulate/work with data however you need
            System.out.println("FILE LINES: ");
            for(String line : fileLines) {
                System.out.println(line);
            }


        } catch(IOException exception) {
            System.out.println("Data from file: " + CSV_FILE + " could not be read.");
            System.out.println(exception.getMessage());
        } finally {
            // ALWAYS want the file to be closed when you're done reading all the data

            try {
                inputStream.close();
            } catch (IOException e) {
                System.out.println("FILE: " + CSV_FILE + " could not be closed.");
            } finally {
                //inputStream.close();        // end up in a loop trying to close the file
            }

        }
    }


    public static void tryWithResourcesExample() {
        /**
         * TRY WITH RESOURCES
         *      - special kind of try/catch that will auto close ANY class that implements the AutoClosable interface
         *      - put the resource that needs to be closed in the parenthesis
         * 
         *      - calls .close() on the InputStream for us
         */
        try(InputStream inputStream = FilesAndDates.class.getClassLoader().getResourceAsStream(CSV_FILE)) {
            byte[] fileBytes = inputStream.readAllBytes();
            System.out.println("RAW FILE BYTES: ");
            System.out.println(fileBytes.toString());

            // decode bytes into characters
            String fileText = new String(fileBytes, StandardCharsets.UTF_8);

            // parsing CSV
            String[] fileLines = parseCSV(fileText);

            // format/manipulate/work with data however you need
            System.out.println("FILE LINES: ");
            for(String line : fileLines) {
                System.out.println(line);
            }
        } catch(IOException exception) {
            System.out.println("Data from file: " + CSV_FILE + " could not be read.");
            System.out.println(exception.getMessage());
        }
    }


    public static void createScheduleReport() {

        List<ScheduleEntry> entries = new LinkedList<>();


        InputStream inputStream = FilesAndDates.class.getClassLoader().getResourceAsStream(CSV_FILE);
        if(inputStream == null) {
            System.out.println("File: " + CSV_FILE + " could not be found.");
            return;
        }

        /**
         * Scanner wraps around the input stream. 
         *      - when it closes, the input stream will close
         *      - can convert bytes into text automatically
         */
        try(Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {

            // skips past the next newline charcater to get to the next line
            scanner.nextLine();         // skip the header row of the CSV

            // read until EOF
            while(scanner.hasNextLine()) {
                
                String line = scanner.nextLine().trim();
                String[] values = line.split(",");

                /**
                 * LocalDate/LocalTime/LocalDateTime
                 *      - now the standard for working with dates and times in Java
                 *      - look at local machine to determine things like timezones and formatting, etc.
                 */
                LocalDate date = LocalDate.parse(values[1].trim());
                LocalTime startTime = LocalTime.parse(values[2].trim());
                LocalTime endTime = LocalTime.parse(values[3].trim());
                String className = values[0].trim();

                // taking the data in the file and turning it into Java objects that can be worked with
                entries.add(new ScheduleEntry(className, date, startTime, endTime));

            }

        } catch(Exception exception) {

        }

    }
}
