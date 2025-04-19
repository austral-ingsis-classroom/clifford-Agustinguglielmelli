package edu.austral.ingsis.clifford.file;

public class FileBuilder {
  private String name;
  private String path;
  private String content;

  public FileBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public FileBuilder setPath(String path) {
    this.path = path;
    return this;
  }

  public FileBuilder setContent(String content) {
    this.content = content;
    return this;
  }

  public File build() {
    return new File(name, path, content);
  }
}
