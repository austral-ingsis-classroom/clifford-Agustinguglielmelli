package edu.austral.ingsis;

import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.FileSystemImpl;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunnerImpl implements FileSystemRunner {
  @Override
  public List<String> executeCommands(List<String> commands) {
    FileSystem fs = new FileSystemImpl();
    List<String> output = new ArrayList<>();

    for (String command : commands) {
      String[] parts =
          command.split(
              " ", 2); // para dividir solo en 2 partes, me queda parte 0 cmd y parte 1 argument
      /*por ej: cd directory lo divido en 2*/
      String cmd = parts[0];
      String arg = parts.length > 1 ? parts[1] : "";

      try {
        switch (cmd) {
          case "ls" -> output.add(String.join(" ", fs.ls(arg)));
          case "mkdir" -> output.add(fs.mkdir(arg));
          case "touch" -> output.add(fs.touch(arg));
          case "cd" -> {
            String result = fs.cd(arg);
            output.add(result);
          }
          case "rm" -> {
            String response = fs.rm(arg);
            output.add(response);
          }
          case "pwd" -> output.add(fs.pwd());
        }
      } catch (Exception e) {
      }
    }
    return output;
  }
}
