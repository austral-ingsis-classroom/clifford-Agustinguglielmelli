package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.commands.Command;
import edu.austral.ingsis.clifford.commands.CommandResult;
import edu.austral.ingsis.clifford.parser.CommandParser;
import java.util.ArrayList;
import java.util.List;

public class FileSystemManager {
  private FileSystem filesystem;
  private final CommandParser parser;

  public FileSystemManager() {
    this.filesystem = new FileSystem();
    this.parser = new CommandParser();
  }

  public List<String> executeCommands(List<String> commandLines) {
    List<String> results = new ArrayList<>();

    for (String commandLine : commandLines) {
      String result = executeSingleCommand(commandLine);
      results.add(result);
    }

    return results;
  }

  private String executeSingleCommand(String commandLine) {

    Command command = parser.parseCommand(commandLine);
    Directory currentDirectory = filesystem.getActual();
    CommandResult result = command.execute(currentDirectory);

    filesystem = filesystem.apply(result);

    return result.getMessage();
  }
}
