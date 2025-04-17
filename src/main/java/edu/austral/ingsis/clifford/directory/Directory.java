package edu.austral.ingsis.clifford.directory;

import edu.austral.ingsis.clifford.file.File;
import edu.austral.ingsis.clifford.filesystem.FileSystemItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Directory implements FileSystemItem {
    String name;
    String path;
    Directory parent;
    Map<String, FileSystemItem> children = new LinkedHashMap<>(); // uso esta estructura porque mantiene orden de insercion


    public Directory(String name, String path, Directory parent) {
        this.name = name;
        this.path = path;
        this.parent = parent;
    }

    public void insertFile(File file) {
        children.put(file.getName(), file);
    }
    public void insertDirectory(Directory directory) {
        children.put(directory.getName(), directory);
    }
    public void printChildren() {
        List<String> names = new ArrayList<>(children.keySet());
        for (String key : names){
            System.out.println(key);
        }
    }
    public void printChildrenAsc() {
        List<String> names = new ArrayList<>(children.keySet());
        Collections.sort(names);
        for (String key : names){
            System.out.println(key);
        }
    }
    public void printChildrenDesc() {
        List<String> names = new ArrayList<>(children.keySet());
        Collections.sort(names, Collections.reverseOrder());
        for (String key : names){
            System.out.println(key);
        }
    }

    public FileSystemItem getChild(String name) {
        return children.get(name);
    }

    public Directory getParent() {
        return parent;
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
