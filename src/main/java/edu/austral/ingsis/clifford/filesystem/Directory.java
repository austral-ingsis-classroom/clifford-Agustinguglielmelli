package edu.austral.ingsis.clifford.filesystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Directory implements FileSystemItem {

  private final String name;
  private final Directory parent;
  private final Map<String, FileSystemItem> children;

  public Directory(String name, Directory parent, Map<String, FileSystemItem> children) {
    this.name = name;
    this.parent = parent;
    this.children = Collections.unmodifiableMap(new LinkedHashMap<>(children));
  }

  public Directory(String name, Directory parent) {
    this(name, parent, new LinkedHashMap<>());
  }

  public Directory() {
    this("/", null, new LinkedHashMap<>());
  }

  @Override
  public String getName() {
    return name;
  }

  public Directory getParent() {
    return parent;
  }

  @Override
  public String getPath() {
    if (parent == null) {
      return "/";
    }
    if (parent.parent == null) {
      return "/" + name;
    }
    return parent.getPath() + "/" + name;
  }

  public FileSystemItem getChild(String elementName) {
    return children.get(elementName);
  }

  public List<FileSystemItem> getChildrenValues() {
    return new ArrayList<>(children.values());
  }

  public Directory addChild(FileSystemItem item) {
    LinkedHashMap<String, FileSystemItem> newChildren = new LinkedHashMap<>(children);
    newChildren.put(item.getName(), item);
    return new Directory(name, parent, newChildren);
  }

  public Directory removeChild(String item) {
    LinkedHashMap<String, FileSystemItem> newChildren = new LinkedHashMap<>(children);
    newChildren.remove(item);
    return new Directory(name, parent, newChildren);
  }
}
