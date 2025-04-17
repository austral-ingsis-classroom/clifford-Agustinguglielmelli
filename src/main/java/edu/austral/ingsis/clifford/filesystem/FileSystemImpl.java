package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.directory.Directory;
import edu.austral.ingsis.clifford.directory.DirectoryBuilder;
import edu.austral.ingsis.clifford.file.File;
import edu.austral.ingsis.clifford.file.FileBuilder;

public class FileSystemImpl implements FileSystem {

    Directory actual;

    public FileSystemImpl() {
        this.actual = createRootDirectory();
    }

    public Directory createRootDirectory() {
        return new DirectoryBuilder()
                .setName("root")
                .setPath("/")
                .build();
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
    public void cd() {

    }

    @Override
    public void touch(String fileName) {
        File file = new FileBuilder()
                .setName(fileName)
                .setContent("")
                .setPath(actual.getPath() + "/" + fileName)
                .build();
        actual.insertFile(file);
        System.out.println("'" + fileName + "' file created");
    }

    @Override
    public void mkdir(String directoryName) {
        Directory directory = new DirectoryBuilder()
                .setName(directoryName)
                .setPath(actual.getPath() + "/" + directoryName)
                .build();
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
}