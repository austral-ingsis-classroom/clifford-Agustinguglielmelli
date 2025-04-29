package edu.austral.ingsis.clifford.filesystem;

public class File implements FileSystemItem {

  private final String name;
  private final Directory parent;
  private final String content;

  public File(String name, Directory parent, String content) {
    this.name = name;
    this.parent = parent;
    this.content = content;
  }

  @Override
  public String getPath() {
    String parentPath = parent.getPath();
    if ("/".equals(parentPath)) {
      return parentPath + name;
    } else return parentPath + "/" + name;
  }

  @Override
  public String getName() {
    return name;
  }

  public Directory getParent() {
    return parent;
  }

  public String getContent() {
    return content;
  }
}
