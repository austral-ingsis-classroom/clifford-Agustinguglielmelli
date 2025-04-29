package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.filesystem.Directory;

public interface Command {
  CommandResult execute(Directory item);
}
