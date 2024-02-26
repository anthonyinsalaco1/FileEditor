import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

/**
 * This program renames all files in a directory with the provided extension to Base64.
 * It can also convert Base64 filenames back to ASCII.
 */
public class FileRenamer {
    public static void main(String[] args) {
        String directoryPath = "\\Y:\\ASAP AAV Screen\\AI additional complete\\"; // TODO: Specify the directory path here

        // MAKE SURE BACKSLASHES ARE DOUBLE BACKSLASH (e.g., \\W:\\path\\to\\folder instead of \W:\path\to\folder)

        String extension = ".ims"; // TODO: Specify the file extension here, with the dot included

        File directory = new File(directoryPath);

        String process = "restoreASCII"; // TODO: Specify the process here, either "encodeBase64" or "restoreASCII"

        if (process.equals("encodeBase64")) {
            capitalizeFilesPart1(directory, extension);
            capitalizeFilesPart2(directory, extension);
            changeToBase64(directory, extension);
        } else if (process.equals("restoreASCII")) {
            changeToASCII(directory, extension);
        }

    }
    private static void changeToBase64(File directory, String extension) {

        // Get all files in the directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {

                // Rename the file only if it is the specified file type
                if (file.isFile() && file.getName().endsWith(extension)) {
                    String oldFileName = file.getName();
                    String newFileName = asciiToBase64(oldFileName, extension);

                    Path sourcePath = file.toPath();
                    Path destinationPath = sourcePath.resolveSibling(newFileName);

                    // Rename the file
                    try {
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File renamed: " + oldFileName + " -> " + newFileName);
                    } catch (IOException e) {
                        System.out.println("Failed to rename file: " + oldFileName);
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    changeToBase64(file, extension);
                }
            }
        }
    }
    private static void changeToASCII(File directory, String extension) {

        // Get all files in the directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {

                // Rename the file only if it is the specified file type
                if (file.isFile() && file.getName().endsWith(extension)) {
                    String oldFileName = file.getName();

                    String newFileName = base64ToAscii(oldFileName, extension);

                    Path sourcePath = file.toPath();
                    Path destinationPath = sourcePath.resolveSibling(newFileName);

                    // Rename the file
                    try {
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File renamed: " + oldFileName + " -> " + newFileName);
                    } catch (IOException e) {
                        System.out.println("Failed to rename file: " + oldFileName);
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    // Recursively process nested directories
                    changeToASCII(file, extension);
                }
            }
        }
    }
    private static void capitalizeFilesPart1(File directory, String extension) {

        // Get all files in the directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {

                // Rename the file only if it is the specified file type
                if (file.isFile() && file.getName().endsWith(extension)) {
                    String oldFileName = file.getName();

                    // first add "a" to the beginning of the file name
                    String newFileName = "a" + oldFileName;
                    Path sourcePath = file.toPath();
                    Path destinationPath = sourcePath.resolveSibling(newFileName);

                    // Rename the file
                    try {
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//                        System.out.println("File renamed: " + oldFileName + " -> " + newFileName);
                    } catch (IOException e) {
                        System.out.println("Failed to rename file: " + oldFileName);
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    capitalizeFilesPart1(file, extension);
                }
            }
        }
    }
    private static void capitalizeFilesPart2(File directory, String extension) {

        // Get all files in the directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {

                // Rename the file only if it is the specified file type
                if (file.isFile() && file.getName().endsWith(extension)) {
                    String oldFileName = file.getName();

                    // remove "a" from the beginning of the file name
                    String newFileName = oldFileName.substring(1);

                    // now, want to capitalize everything in file name but not the .extension
                    newFileName = newFileName.substring(0, newFileName.lastIndexOf('.')).toUpperCase() + extension;
                    Path sourcePath = file.toPath();
                    Path destinationPath = sourcePath.resolveSibling(newFileName);

                    // Rename the file
                    try {
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//                        System.out.println("File renamed: " + oldFileName + " -> " + newFileName);
                    } catch (IOException e) {
                        System.out.println("Failed to rename file: " + oldFileName);
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    capitalizeFilesPart2(file, extension);
                }
            }
        }
    }
    private static String asciiToBase64(String fileName, String extension) {
        // Remove the file extension
        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        // Convert the name to Base64
        String base64Name = Base64.getEncoder().encodeToString(nameWithoutExtension.getBytes(StandardCharsets.UTF_8));
        // Append the file extension back
        return base64Name + extension;
    }
    public static String base64ToAscii(String base64Filename, String extension) {
        // Remove the file extension
        String nameWithoutExtension = base64Filename.substring(0, base64Filename.lastIndexOf('.'));

        // Decode the Base64 string
        byte[] decodedBytes = Base64.getDecoder().decode(nameWithoutExtension.getBytes(StandardCharsets.UTF_8));

        // Convert the decoded bytes to ASCII string
        String asciiFilename = new String(decodedBytes, StandardCharsets.US_ASCII);

        // Append the file extension back
        return asciiFilename + extension;
    }

}
