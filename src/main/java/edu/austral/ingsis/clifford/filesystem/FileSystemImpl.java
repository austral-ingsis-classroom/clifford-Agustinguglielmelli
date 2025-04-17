package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.directory.Directory;
import edu.austral.ingsis.clifford.directory.DirectoryBuilder;
import edu.austral.ingsis.clifford.file.File;
import edu.austral.ingsis.clifford.file.FileBuilder;

public class FileSystemImpl implements FileSystem {

    Directory actual;

    public FileSystemImpl() {
        this.actual = createRoot();
    }

    public Directory createRoot() {
        return new DirectoryBuilder()
                .setName("root")
                .setPath("/")
                .build();
    }

    @Override
    public void ls() {

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
        actual.insert(file);
        System.out.println("'" + fileName + "' file created");
    }

    @Override
    public void mkdir() {

    }

    @Override
    public void rm() {

    }

    @Override
    public void pwd() {

    }
}