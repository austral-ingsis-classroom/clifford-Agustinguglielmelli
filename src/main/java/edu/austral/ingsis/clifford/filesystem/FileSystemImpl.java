package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.directory.Directory;
import edu.austral.ingsis.clifford.directory.DirectoryBuilder;
import edu.austral.ingsis.clifford.file.File;
import edu.austral.ingsis.clifford.file.FileBuilder;

import java.util.ArrayList;
import java.util.List;

public class FileSystemImpl implements FileSystem {
    Directory root;
    Directory actual;

    public FileSystemImpl() {
        this.root = createRootDirectory();
        this.actual = root;
    }

    @Override
    public void ls(String flag) {
        if (flag.isEmpty()) {
            actual.printChildren();
        }
        switch (flag) {
            case "--ord=asc":
                actual.printChildrenAsc();
                break;
            case "--ord=desc":
                actual.printChildrenDesc();
                break;
        }
    }

    @Override
    public void cd(String path) {
        if (path.equals(".")) {
            return;
        }

        if (path.equals("..")) {
            if (actual.getParent() != null) {
                actual = actual.getParent();
                System.out.println("Moved to directory: " + actual.getName());
            } else {
                System.out.println("Already at root directory");
            }
            return;
        }

        if (path.startsWith("/")) { // ruta absoluta, empiezo desde la raíz
            Directory temp = root;
            List<String> directories = getDirectories(path);

            for (String dir : directories) {
                FileSystemItem fileSystemItem = temp.getChild(dir);
                if (fileSystemItem == null) {
                    System.out.println("No such directory: " + dir);
                    return;
                } else if (fileSystemItem instanceof File) {
                    System.out.println(dir + " is not a directory");
                    return;
                } else if (fileSystemItem instanceof Directory) {
                    temp = (Directory) fileSystemItem;
                }
            }
            actual = temp;
            System.out.println("Moved to directory: " + path);
            return;
        }

        //si es una ruta relativa, osea desde el actual
        FileSystemItem fileSystemItem = actual.getChild(path);
        if (fileSystemItem == null) {
            System.out.println("No such directory: " + path);
        } else if (fileSystemItem instanceof File) {
            System.out.println(path + " is not a directory");

        } else if (fileSystemItem instanceof Directory) {
            actual = (Directory) fileSystemItem;
            System.out.println("Moved to directory: " + path);
        }
    }


    @Override
    public void touch(String fileName) throws IllegalArgumentException{
        File file = new FileBuilder()
                .setName(fileName)
                .setContent("")
                .setPath(actual.getPath() + "/" + fileName)
                .build();
        if (file.getName().contains("/") || file.getName().contains(" ")) {
            throw new IllegalArgumentException("Invalid file name: Cannot contain / or spaces");
        }
        actual.insertFile(file);
        System.out.println("'" + fileName + "' file created");
    }

    @Override
    public void mkdir(String directoryName) throws IllegalArgumentException {
        Directory directory = new DirectoryBuilder()
                .setName(directoryName)
                .setPath(actual.getPath() + "/" + directoryName)
                .setParent(actual)
                .build();
        if (directory.getName().contains("/") || directory.getName().contains(" ")) {
            throw new IllegalArgumentException("Invalid directory name: Cannot contain / or spaces");
        }
        actual.insertDirectory(directory);
        System.out.println("'" + directoryName + "' directory created");
    }

    @Override
    public void rm() {

    }

    @Override
    public void pwd() {
        System.out.println(actual.getPath());
    }

    public Directory createRootDirectory() {
        return new DirectoryBuilder()
                .setName("root")
                .setPath("/")
                .setParent(null)
                .build();
    }

    public List<String> getDirectories(String path) {
        String[] parts = path.split("/");

        List<String> result = new ArrayList<>();

        for (String part : parts) {
            if (!part.isEmpty()) {
                result.add(part);
            }
        }
        return result;
    }

}