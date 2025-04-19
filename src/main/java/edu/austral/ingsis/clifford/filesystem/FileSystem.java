package edu.austral.ingsis.clifford.filesystem;

import java.util.List;

public interface FileSystem {

  List<String> ls(String flag);

  String cd(String path);

  String touch(String itemName);

  String mkdir(String itemName);

  String rm(String itemName);

  String pwd();
}
