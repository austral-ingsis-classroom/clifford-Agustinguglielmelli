package edu.austral.ingsis.clifford.parser;

import edu.austral.ingsis.clifford.commands.Cd;
import edu.austral.ingsis.clifford.commands.Command;
import edu.austral.ingsis.clifford.commands.Ls;
import edu.austral.ingsis.clifford.commands.Mkdir;
import edu.austral.ingsis.clifford.commands.Pwd;
import edu.austral.ingsis.clifford.commands.Rm;
import edu.austral.ingsis.clifford.commands.Touch;
import java.util.NoSuchElementException;

public class CommandParser {

  public Command parseCommand(String input) throws NoSuchElementException {

    String[] parts = input.split(" ", 2);
    String command = parts[0];

    String args = parts.length > 1 ? parts[1] : "";

    switch (command) {
      case "ls" -> {
        if (args.startsWith("--ord=")) {
          return new Ls(args.substring(6));
        } else {
          return new Ls();
        }
      }
      case "cd" -> {
        return new Cd(args);
      }
      case "touch" -> {
        return new Touch(args);
      }
      case "mkdir" -> {
        return new Mkdir(args);
      }
      case "rm" -> {
        if (args.startsWith("--recursive")) {
          String targetName = args.split(" ", 2).length > 1 ? args.split(" ", 2)[1] : "";
          return new Rm(targetName, true);
        } else {
          return new Rm(args);
        }
      }
      case "pwd" -> {
        return new Pwd();
      }
      default -> throw new NoSuchElementException("Unknown command");
    }
  }
}
