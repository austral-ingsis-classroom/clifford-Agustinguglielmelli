package edu.austral.ingsis.clifford.directory;

import edu.austral.ingsis.clifford.file.File;
import edu.austral.ingsis.clifford.filesystem.FileSystemItem;

import java.util.HashMap;
import java.util.Map;

public class Directory implements FileSystemItem {
    String name;
    String path;
    Map<String, FileSystemItem> children = new HashMap<>();

    public Directory(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public void insert(File file) {
        children.put(file.getName(), file);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }
}
