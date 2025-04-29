package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.commands.CommandResult;

public class FileSystem {
  private final Directory root;
  private final Directory actual;

  public FileSystem() { // para fileSYstem vacio
    this.root = new Directory();
    this.actual = root;
  }

  public FileSystem(Directory root, Directory actual) {
    this.root = root;
    this.actual = actual;
  }

  private Directory findDirectory(Directory search, String path) {
    if (path.equals("/")) {
      return root;
    }

    if (!path.startsWith("/")) {
      path = "/" + path;
    }

    String[] components = path.split("/");
    Directory current = search;

    for (int i = 1; i < components.length; i++) {
      String component = components[i];
      if (component.isEmpty()) continue;

      FileSystemItem item = current.getChild(component);
      if (!(item instanceof Directory)) {
        return null;
      }

      current = (Directory) item;
    }

    return current;
  }

  public FileSystem apply(CommandResult result) {
    if (result.getNewRoot() == null) {
      Directory targetDir = result.getCurrentDirectory();

      if (targetDir != root && targetDir != actual) {
        Directory resolvedDir = findDirectory(root, targetDir.getPath());
        if (resolvedDir != null) {
          return new FileSystem(root, resolvedDir);
        }
      }

      return new FileSystem(root, targetDir);
    }

    return new FileSystem(result.getNewRoot(), result.getCurrentDirectory());
  }

  public Directory getActual() {
    return actual;
  }

  public Directory getRoot() {
    return root;
  }

  public static Directory rebuildRoot(Directory root, Directory directory) {
    if (root.getParent() == null) {
      return directory;
    }

    Directory newParent = root.getParent().removeChild(root.getName());
    newParent = newParent.addChild(directory);

    return rebuildRoot(root.getParent(), newParent);
  }
}
