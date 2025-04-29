package edu.austral.ingsis;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;
import java.util.List;

public class FileSystemRunnerImpl implements FileSystemRunner {
  private final FileSystemManager manager = new FileSystemManager();

  @Override
  public List<String> executeCommands(List<String> commands) {
    return manager.executeCommands(commands);
  }
}
