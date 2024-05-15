package web.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

@Slf4j
public class FileOperations {
    public static void copyAndRenameFile(String originalFilePath, String newFilePath, String newFileName) {
        try {
            Path originalPath = Paths.get(originalFilePath);
            Path newPath = Paths.get(newFilePath);

            copyFile(originalPath, newPath);
            renameFile(newPath,  newFileName);

            log.info("File copied from " + originalFilePath + "\n to "
                     + newFilePath + " and renamed to " + newFileName + " successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(Path source, Path destination) throws IOException {
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void renameFile(Path file, String newName) throws IOException {
        Path directory = file.getParent();
        Path renamedFile = directory.resolve(newName);
        Files.move(file, renamedFile);
    }

    public static void deleteFile(String filePath) {
        try {
            Path fileToDelete = Paths.get(filePath);
            Files.delete(fileToDelete);
//            log.info("File " + filePath + " deleted successfully.");
        } catch (IOException e) {
//            log.error("Unable to delete " + filePath + ". File not found or not exist.\n");
//                    + " Exception: "+ e.getMessage());
//            e.printStackTrace();
        }
    }

    public static void deleteFolder(String folderPath) {
        try {
            Path directory = Paths.get(folderPath);

            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
//            log.info("Folder " + folderPath + " deleted successfully.");
        } catch (IOException e) {
//            log.error("Unable to delete " + folderPath + ". Folder not found or not exist.\n");
//                    + " Exception: "+ e.getMessage());
//            e.printStackTrace();
        }
    }

    public static void deleteFilesWithPrefixInDirectory(String directoryPath, String fileNamePrefix) {
        try (Stream<Path> pathStream = Files.list(Paths.get(directoryPath))) {
            pathStream
                    .filter(path -> path.getFileName().toString().startsWith(fileNamePrefix))
                    .forEach(path -> deleteFile(path.toString()));
        } catch (IOException e) {
            log.error("Failed to delete files with prefix: " + fileNamePrefix + ", in directory: " + directoryPath, e);
            throw new RuntimeException("Failed to list files in directory: " + directoryPath, e);
        }
    }

}
