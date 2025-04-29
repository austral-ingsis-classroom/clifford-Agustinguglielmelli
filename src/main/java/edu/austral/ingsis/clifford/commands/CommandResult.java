package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.filesystem.Directory;

public class CommandResult {
  private final Directory newRoot;
  private final Directory currentDirectory;
  private final String message;

  public CommandResult(Directory newRoot, Directory currentDirectory, String message) {
    this.newRoot = newRoot;
    this.currentDirectory = currentDirectory;
    this.message = message;
  }

  public Directory getNewRoot() {
    return newRoot;
  }

  public Directory getCurrentDirectory() {
    return currentDirectory;
  }

  public String getMessage() {
    return message;
  }

  public static CommandResult noRootChange(
      Directory currentDirectory, String message) { // para cd que no cambia raiz
    return new CommandResult(null, currentDirectory, message);
  }
}
