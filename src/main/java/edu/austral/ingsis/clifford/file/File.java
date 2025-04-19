package edu.austral.ingsis.clifford.file;

import edu.austral.ingsis.clifford.filesystem.FileSystemItem;

public class File implements FileSystemItem {
  String name;
  String path;
  String content;

  public File(String name, String path, String content) {
    this.name = name;
    this.path = path;
    this.content = content;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getPath() {
    return path;
  }

  public String getContent() {
    return content;
  }
}
