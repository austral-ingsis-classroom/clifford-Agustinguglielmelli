package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.filesystem.FileSystemImpl;

public class Main {
  public static void main(String[] args) {
    FileSystemImpl fileSystem = new FileSystemImpl();
    System.out.println("-----------INICIO----------------");
    fileSystem.touch("hola.txt");
    fileSystem.touch("a.txt");
    fileSystem.mkdir("mi-directorio-dir");
    fileSystem.cd("mi-directorio-dir");
    fileSystem.touch("archivo.txt");
    fileSystem.ls("");
    fileSystem.mkdir("directorioUltimo");
    fileSystem.ls("");
    fileSystem.cd("directorioUltimo");
    fileSystem.ls("");
    fileSystem.cd("..");
    fileSystem.rm("--recursive directorioUltimo");
    fileSystem.ls("");
    System.out.println("---------FINAL--------------");
  }
}
