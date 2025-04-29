package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.FileSystemItem;

public class Cd implements Command {
  String path;

  public Cd(String path) {
    this.path = path;
  }

  @Override
  public CommandResult execute(Directory actual) {
    if (isCurrentDirectoryPath(path)) { // .
      return handleCurrentDirectory(actual);
    }

    if (isMoveToParentPath()) { // ..
      return handleMoveToParentPath(actual);
    }

    if (isAbsolutePath()) { // "/"
      return handleAbsolutePath(actual);
    }

    return navigatePath(actual, path);
  }

  private CommandResult handleAbsolutePath(Directory actual) {
    Directory root = getRootDirectory(actual);

    if (path.equals("/")) {
      return CommandResult.noRootChange(root, "Moved to directory: '/'");
    }

    String relativePath = path.substring(1);
    return navigatePath(root, relativePath);
  }

  private boolean isAbsolutePath() {
    return path.startsWith("/");
  }

  private static CommandResult handleMoveToParentPath(Directory actual) {
    Directory parent = actual.getParent();
    if (parent == null) {
      return CommandResult.noRootChange(actual, "Moved to directory: '/'");
    }
    return CommandResult.noRootChange(parent, "Moved to directory: '" + parent.getName() + "'");
  }

  private boolean isMoveToParentPath() {
    return path.equals("..");
  }

  private static CommandResult handleCurrentDirectory(Directory actual) {
    return CommandResult.noRootChange(actual, "Moved to directory: '.'");
  }

  private Directory getRootDirectory(Directory dir) {
    Directory currentDir = dir;
    while (currentDir.getParent() != null) {
      currentDir = currentDir.getParent();
    }
    return currentDir;
  }

  private CommandResult navigatePath(Directory dir, String path) {
    String[] directories = path.split("/");
    Directory currentDir = dir;

    for (String directory : directories) {
      if (ignore(directory)) continue;

      if (isParent(directory)) {
        currentDir = moveToParent(currentDir);
        continue;
      }

      CommandResult moveResult = moveToChild(currentDir, directory);
      if (moveResult != null) return moveResult;

      currentDir = (Directory) currentDir.getChild(directory);
    }

    return successResult(currentDir);
  }

  private boolean isCurrentDirectoryPath(String path) {
    return path.isEmpty() || path.equals(".");
  }

  private static boolean ignore(String directory) {
    if (directory.isEmpty() || directory.equals(".")) {
      return true;
    }
    return false;
  }

  private boolean isParent(String component) { // me fijo si is parent (los dos .. son ir al parent)
    return component.equals("..");
  }

  private Directory moveToParent(Directory dir) {
    if (dir.getParent() != null) return dir.getParent();
    return dir;
  }

  private CommandResult moveToChild(Directory dir, String name) {
    FileSystemItem nextElement = dir.getChild(name);

    if (nextElement == null) {
      return CommandResult.noRootChange(dir, "'" + name + "' directory does not exist");
    }

    if (!(nextElement instanceof Directory)) {
      return CommandResult.noRootChange(dir, "'" + name + "' is not a directory");
    }
    return null; // no hubo error pero tengo que seguir recorriendo
  }

  private static CommandResult successResult(Directory currentDir) {
    return CommandResult.noRootChange(
        currentDir,
        "Moved to directory: '"
            + (currentDir.getName().equals("/") ? "/" : currentDir.getName())
            + "'");
  }
}
