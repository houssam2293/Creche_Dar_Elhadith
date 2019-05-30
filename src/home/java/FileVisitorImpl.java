package home.java;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class FileVisitorImpl implements FileVisitor<Path> {
    private String fileName;
    private Path startDir;
    private String filePath;

    @Override
    public FileVisitResult preVisitDirectory(
            Path dir, BasicFileAttributes attrs) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(
            Path file, BasicFileAttributes attrs) {
        String filename = file.getFileName().toString();

        if (fileName.equals(filename)) {
            System.out.println("File found: " + file.toString());
            filePath = file.toString();
            return TERMINATE;
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(
            Path file, IOException exc) {
        System.out.println("Failed to access file: " + file.toString());
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(
            Path dir, IOException exc) throws IOException {
        boolean finishedSearch = Files.isSameFile(dir, startDir);
        if (finishedSearch) {
            System.out.println("File:" + fileName + " not found");
            return TERMINATE;
        }
        return CONTINUE;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Path getStartDir() {
        return startDir;
    }

    public void setStartDir(Path startDir) {
        this.startDir = startDir;
    }

    public String getFilePath() {
        return filePath;
    }
}