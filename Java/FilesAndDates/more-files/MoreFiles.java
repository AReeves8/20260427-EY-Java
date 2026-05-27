import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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

        try {

            // creates a new directory in your machines "TEMP" folder
            //TEMP_ROOT = Files.createTempDirectory("nio2-demo"); 

            // creating a path to the current working directory
            Path workingDirectory = Path.of("C:", "SkillStorm-Project-Repos", "20260427-EY-Java", "Java", "FilesAndDates", "more-files", "outputs");
            TEMP_ROOT = Files.createDirectories(workingDirectory); 
            System.out.println(TEMP_ROOT);

            // creating a path to a sample file
            Path sampleFile = TEMP_ROOT.resolve("sample.txt");

            // no longer need try-with-resources to close the file - Files will open and close for you
            Files.writeString(sampleFile, "Alice,Bob,Charlie,Devon,Elaine");
            Files.writeString(sampleFile, "\nFred,Greg,Hariott", StandardOpenOption.APPEND);    // use OpenOptions to do appends
            
            // reading from files is also easy
            String fileContent = Files.readString(sampleFile);
            System.out.println(fileContent);

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
}
