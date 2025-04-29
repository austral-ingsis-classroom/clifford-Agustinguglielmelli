package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.auxclasses.NameValidator;
import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class Mkdir implements Command {
  String directoryName;

  public Mkdir(String directoryName) {
    this.directoryName = directoryName;
  }

  @Override
  public CommandResult execute(Directory actual) {
    if (directoryName.isEmpty()) {
      return CommandResult.noRootChange(actual, "No directory name provided");
    }

    if (NameValidator.isNameInvalid(
        directoryName)) { // reutilizo la funcion de isnamevalid aca y en touch
      return CommandResult.noRootChange(actual, "Invalid directory name");
    }

    if (hasChildNamed(actual)) {
      return CommandResult.noRootChange(actual, "'" + directoryName + "' already exists");
    }

    return createNewDirectory(actual);
  }

  private CommandResult createNewDirectory(Directory actual) {
    Directory newDirectory = new Directory(directoryName, actual);
    Directory updatedCurrentDir = actual.addChild(newDirectory);
    Directory newRoot = FileSystem.rebuildRoot(actual, updatedCurrentDir);

    return new CommandResult(
        newRoot, updatedCurrentDir, "'" + directoryName + "' directory created");
  }

  private boolean hasChildNamed(Directory actual) {
    return actual.getChild(directoryName) != null;
  }
}
