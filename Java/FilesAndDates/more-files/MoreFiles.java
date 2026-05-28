import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class MoreFiles {

    // creating a temporary "root" directory we can build off of
    static Path TEMP_ROOT;          


    public static void main(String[] args) {

        /**
         * java.io vs java.nio.2
         * 
         *      java.io.File    - original Files API
         *          - many methods just returned booleans in all scenarios
         *              - ex: File.delete() returned false if the file couldn't be deleted
         *          - no way to easily get File attributes/metadata
         *          - building file paths with raw strings - easy to make mistakes
         * 
         *      java.nio.2
         *          - most methods throw IOExceptions now - gives more detail about failures
         *          - BasicFileAttributes and PosixFileAttributes for attributes and metadata
         *          - Path interface for managing file paths
         *              - immutable, making it thread-safe
         *          - Files instead of File
         * 
         *      generally, modern code prefers Path and Files to anything from java.io.File
         */


        // creates a new directory in your machines "TEMP" folder
        //TEMP_ROOT = Files.createTempDirectory("nio2-demo"); 

        // creating a path to the current working directory
        try {
            Path workingDirectory = Path.of("C:", "SkillStorm-Project-Repos", "20260427-EY-Java", "Java", "FilesAndDates", "more-files", "outputs");
            TEMP_ROOT = Files.createDirectories(workingDirectory);
            System.out.println(TEMP_ROOT);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        // DEMOS:
        //nioBasics();
        fileWalking();

    }

    static void nioBasics() {
        try {
            // creating a path to a sample file
            Path sampleFile = TEMP_ROOT.resolve("sample.txt");

            /**
             * WRITING TO FILES
             *      - no longer need try-with-resources to close the file - Files will open and close for you
             *      - StandardOpenOptions:
             *          - APPEND - appends to the end of a file rather than overwriting 
             *          - CREATE - create the file if absent, open if present (default option for writeString)
             *          - CREATE_NEW - create the file if absent, throws FileAlreadyFoundException if the file is present
             *          - TRUNCATE_EXISTING - erase file content before writing (works with CREATE)
             */
            Files.writeString(sampleFile, "Alice,Bob,Charlie,Devon,Elaine");
            Files.writeString(sampleFile, "\nFred,Greg,Hariott", StandardOpenOption.APPEND);    // use OpenOptions to do appends

            // CREATE starts overwriting what is there, BUT DOES NOT CLEAR THE FILE FIRST
            Files.writeString(sampleFile, "Strawberry, Banana, Orange", StandardOpenOption.CREATE);       

            // use TRUNCATE_EXISTING to ensure the file is completely empty before writing to it
            String fruits = "Cherry, Grape, Lemon\nStrawberry, Banana, Orange\nPineapple, Apple, Mango";
            Files.writeString(sampleFile, fruits, StandardOpenOption.TRUNCATE_EXISTING); 

            
            /**
             * READING TO FILES
             *      - reading from files is also easy
             *      - readString - store the entire file as one long string
             *      - readAllLines - store the entire file as a List<String> where each element is the next line of the file
             * 
             */
            String fileContent = Files.readString(sampleFile);
            System.out.println("ENTIRE FILE: \n" + fileContent);

            List<String> fileLines = Files.readAllLines(sampleFile);
            System.out.println("FILE LINES:");
            fileLines.stream().forEach(System.out::println);

            // reading and writing raw bytes to a file
            byte[] rawFileBytes = Files.readAllBytes(sampleFile);
            System.out.println("FILE BYTES: " + rawFileBytes);
            System.out.println("FIRST BYTE: " + rawFileBytes[0]);
            Files.write(sampleFile, rawFileBytes);


            /**
             * MOVING AND COPYING FILES
             *      - java.nio.2 has easy ways to copy and move files to different paths
             *      - .move()
             *      - .copy()
             * 
             *      - StandardCopyOptions
             *          - ATOMIC_MOVE 
             *              - checks that the file name is valid for the current system and if the file exists alrady or not
             *                  - otherwise it reverts to doing a Copy and Delete
             *          - REPLACE_EXISTING - replace an existing file when copying/moving. 
             *              - default option throws FileAlreadyExistsException if the target destination is present
             */
            Path backupFile = TEMP_ROOT.resolve("sample-2.txt");
            Files.copy(sampleFile, backupFile);

            // of the file already exists, need to use REPLACE_EXISTING
            Path backupFile2 = TEMP_ROOT.resolve("sample-3.txt");
            Files.createFile(backupFile2);
            Files.copy(sampleFile, backupFile2, StandardCopyOption.REPLACE_EXISTING);

            // moving /outputs/sample3.txt  ->  /outputs/backups/backup-sample.txt
            Path newDir = Files.createDirectories(TEMP_ROOT.resolve("backups"));
            Files.move(backupFile2, newDir.resolve("backup-sample.txt"), StandardCopyOption.ATOMIC_MOVE);


            // can convert between old Files API and new
            File oldWay = sampleFile.toFile();
            Path newWay = oldWay.toPath();

            // Path API can give you info on the file itself
            System.out.println("File name: " + newWay.getFileName());
            System.out.println("Parent name: " + newWay.getParent());
            System.out.println("Parent name: " + newWay.getRoot());
            System.out.println("Directories Count: " + newWay.getNameCount());
            System.out.println("Directory[n]: " + newWay.getName(3));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void fileWalking()  {

        Path root = TEMP_ROOT.resolve("file-walking-demo");

        /**
         * FILE WALKING
         *      - use Streams to walk through each layer of the file system
         *      - can perform different operations at each layer
         * 
         *      - does a Depth First Search (DFS) through file tree
         */

        try {
            Path salesReports = Files.createDirectories(root.resolve("sales-reports"));
            Path sales2025 = Files.createDirectories(salesReports.resolve("2025"));
            Path sales2026 = Files.createDirectories(salesReports.resolve("2026"));
            Files.writeString(sales2025.resolve("q4-report.txt"), "Q4 REPORT");
            Files.writeString(sales2026.resolve("q1-report.txt"), "Q1 REPORT");
            Files.writeString(sales2026.resolve("q2-report.txt"), "Q2 REPORT");
            Files.writeString(sales2026.resolve("q2-budget.csv"), "Q2 BUDGET");

            try(Stream<Path> fileStream = Files.walk(root)) {
                System.out.println("ALL FILES FROM ROOT: ");

                // relativize gives the relative file path between two points
                fileStream.forEach(path -> System.out.println("\t" + root.relativize(path)));
            }

            // filter only .txt Files
            try(Stream<Path> fileStream = Files.walk(root)) {
                System.out.println("TXT FILES ONLY: ");

                fileStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(path -> System.out.println("\t" + root.relativize(path)));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }


}
