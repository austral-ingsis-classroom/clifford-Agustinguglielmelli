package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.directory.Directory;
import edu.austral.ingsis.clifford.directory.DirectoryBuilder;
import edu.austral.ingsis.clifford.file.File;
import edu.austral.ingsis.clifford.file.FileBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileSystemImpl implements FileSystem {
  Directory root;
  Directory actual;

  public FileSystemImpl() {
    this.root = createRootDirectory();
    this.actual = root;
  }

  @Override
  public List<String> ls(String flag) {
    List<String> output = new ArrayList<>();
    Map<String, FileSystemItem> children = actual.getChildren();

    if (flag.isEmpty()) {
      output.addAll(children.keySet());
    }
    switch (flag) {
      case "--ord=asc":
        output.addAll(actual.getChildrenAsc());
        break;
      case "--ord=desc":
        output.addAll(actual.getChildrenDesc());
        break;
    }
    return output;
  }

  @Override
  public String cd(String path) {
    if (path.equals(".")) {
      return "";
    }

    if (path.equals("..")) {
      actual = actual.getParent();
      return "Moved to directory: '/'";
    }

    if (path.startsWith("/")) { // ruta absoluta, empiezo desde la raíz
      Directory temp = root;
      List<String> directories = getDirectories(path);

      for (String dir : directories) {
        FileSystemItem fileSystemItem = temp.getChild(dir);
        if (fileSystemItem == null) {
          return "'" + dir + "' directory does not exist";

        } else if (fileSystemItem instanceof File) {
          return dir + " is not a directory";
        } else if (fileSystemItem instanceof Directory) {
          temp = (Directory) fileSystemItem;
        }
      }
      actual = temp;
      return "Moved to directory: '" + path + "'";
    } else {
      // si es una ruta relativa, osea desde el actual
      String[] directories = path.split("/");
      String lastDir =
          directories[directories.length - 1]; // este es el que devuevlo en el moved to al final
      for (String dir : directories) {
        FileSystemItem fileSystemItem = actual.getChild(dir);
        if (fileSystemItem == null) {
          return "'" + dir + "' directory does not exist";
        } else if (fileSystemItem instanceof File) {
          return path + " is not a directory";
        } else {
          actual = (Directory) fileSystemItem;
        }
      }
      return "Moved to directory: " + "'" + lastDir + "'";
    }
  }

  @Override
  public String touch(String fileName) throws IllegalArgumentException {
    File file =
        new FileBuilder()
            .setName(fileName)
            .setContent("")
            .setPath(actual.getPath() + "/" + fileName)
            .build();
    if (file.getName().contains("/") || file.getName().contains(" ")) {
      throw new IllegalArgumentException("Invalid file name: Cannot contain / or spaces");
    }
    actual.insertFile(file);
    return "'" + fileName + "' file created";
  }

  @Override
  public String mkdir(String directoryName) throws IllegalArgumentException {
    Directory directory =
        new DirectoryBuilder()
            .setName(directoryName)
            .setPath(actual.getPath() + "/" + directoryName)
            .setParent(actual)
            .build();
    if (directory.getName().contains("/") || directory.getName().contains(" ")) {
      throw new IllegalArgumentException("Invalid directory name: Cannot contain / or spaces");
    }
    actual.insertDirectory(directory);
    return "'" + directoryName + "' directory created";
  }

  @Override
  public String rm(String itemName) {
    boolean recursive = false; // si recursive es false nopuedo eliminar un directorio
    String targetName;

    if (itemName.startsWith("--recursive ")) {
      recursive = true;
      targetName = itemName.replaceFirst("--recursive", "").trim();
    } else {
      targetName = itemName.trim();
    }

    FileSystemItem item = actual.getChild(targetName);
    if (item == null) {
      return "No such file or directory: " + targetName;
    }
    if (item instanceof File) {
      actual.getChildren().remove(targetName);
      return "'" + targetName + "' removed";

    } else if (item instanceof Directory) {
      if (!recursive) {
        return "Cannot remove '" + itemName + "', is a directory";
      } else {
        actual.getChildren().remove(targetName);
        return "'" + targetName + "' removed";
      }
    }
    return "";
  }

  @Override
  public String pwd() {
    return actual.getPath();
  }

  public Directory createRootDirectory() {
    return new DirectoryBuilder().setName("root").setPath("").setParent(null).build();
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
