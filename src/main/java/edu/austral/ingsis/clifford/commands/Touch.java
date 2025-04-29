package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.auxclasses.NameValidator;
import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.File;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class Touch implements Command {
  String name;

  public Touch(String name) {
    this.name = name;
  }

  @Override
  public CommandResult execute(Directory actual) {
    if (name.isEmpty()) {
      return CommandResult.noRootChange(actual, "Must specify a file name");
    }

    if (NameValidator.isNameInvalid(name)) {
      return CommandResult.noRootChange(actual, "Filename cannot contain spaces or '/'");
    }

    if (hasChildNamed(actual)) {
      return CommandResult.noRootChange(actual, "'" + name + "' already exists");
    }

    return createNewFile(actual);
  }

  private CommandResult createNewFile(Directory actual) {
    File newFile = new File(name, actual, "");

    Directory updatedCurrentDir = actual.addChild(newFile);

    Directory newRoot = FileSystem.rebuildRoot(actual, updatedCurrentDir);

    return new CommandResult(newRoot, updatedCurrentDir, "'" + name + "' file created");
  }

  private boolean hasChildNamed(Directory actual) {
    return actual.getChild(name) != null;
  }
}
