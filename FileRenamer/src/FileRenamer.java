import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class FileRenamer {
    public static void main(String[] args) {

        File directory = new File("/path/to/directory");
        String extension = ".ims";

//        String process = "change to base 64";
        if (process == "change to base 64") {
            renameFiles(directory, extension, Action.CAPITALIZE_PART1);
            renameFiles(directory, extension, Action.CAPITALIZE_PART2);
            renameFiles(directory, extension, Action.CHANGE_TO_BASE64);
        } else if (process == "change to ascii") {
            renameFiles(directory, extension, Action.CHANGE_TO_ASCII;
        }
    }
    

    public static void renameFiles(File directory, String extension, Action action) {
        // Get all files in the directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(extension)) {
                    String oldFileName = file.getName();
                    String newFileName = "";

                    switch (action) {
                        case CHANGE_TO_BASE64:
                            newFileName = asciiToBase64(oldFileName, extension);
                            break;
                        case CHANGE_TO_ASCII:
                            newFileName = base64ToAscii(oldFileName, extension);
                            break;
                        case CAPITALIZE_PART1:
                            newFileName = "a" + oldFileName;
                            break;
                        case CAPITALIZE_PART2:
                            newFileName = capitalizeFileNamePart2(oldFileName, extension);
                            break;
                        default:
                            break;
                    }

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
                    renameFiles(file, extension, action);
                }
            }
        }
    }

    /**
     * Convert the file name to base64
     */
    private static String asciiToBase64(String fileName, String extension) {
        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String base64Name = Base64.getEncoder().encodeToString(nameWithoutExtension.getBytes(StandardCharsets.UTF_8));
        return base64Name + extension;
    }

    /**
     * Convert the file name from base64 to ascii
     */
    private static String base64ToAscii(String base64Filename, String extension) {
        String nameWithoutExtension = base64Filename.substring(0, base64Filename.lastIndexOf('.'));
        byte[] decodedBytes = Base64.getDecoder().decode(nameWithoutExtension.getBytes(StandardCharsets.UTF_8));
        String asciiFilename = new String(decodedBytes, StandardCharsets.US_ASCII);
        return asciiFilename + extension;
    }

    /**
     * Capitalize the first letter of the file name and remove the first letter of the file name
     */
    private static String capitalizeFileNamePart2(String fileName, String extension) {
        String newFileName = fileName.substring(1);
        newFileName = newFileName.substring(0, newFileName.lastIndexOf('.')).toUpperCase() + extension;
        return newFileName;
    }

    // Enum to represent different actions
    public enum Action {
        CHANGE_TO_BASE64,
        CHANGE_TO_ASCII,
        CAPITALIZE_PART1,
        CAPITALIZE_PART2
    }

}
