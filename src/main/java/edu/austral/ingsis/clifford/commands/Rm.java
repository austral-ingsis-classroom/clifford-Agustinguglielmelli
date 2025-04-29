package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.FileSystemItem;

public class Rm implements Command {
  private final String name;
  private final boolean recursive;

  public Rm(String name) {
    this(name, false);
  }

  public Rm(String name, boolean recursive) {
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public CommandResult execute(Directory actual) {
    if (name.isEmpty()) {
      return CommandResult.noRootChange(actual, "No element name provided");
    }

    FileSystemItem target = actual.getChild(name);

    if (target == null) {
      return CommandResult.noRootChange(actual, "'" + name + "' does not exist");
    }

    if (target instanceof Directory && !recursive) {
      return CommandResult.noRootChange(actual, "Cannot remove '" + name + "', is a directory");
    }

    Directory newActual = actual.removeChild(name);
    Directory newRoot = FileSystem.rebuildRoot(actual, newActual);

    return new CommandResult(newRoot, newActual, "'" + name + "' removed");
  }
}
