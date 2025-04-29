package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.filesystem.Directory;

public class Pwd implements Command {
  public Pwd() {}

  @Override
  public CommandResult execute(Directory directory) {

    String location = directory.getPath();
    return CommandResult.noRootChange(directory, location);
  }
}
