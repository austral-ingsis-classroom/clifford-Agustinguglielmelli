package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.filesystem.FileSystemImpl;


public class Main {
    public static void main(String[] args) {
        FileSystemImpl fileSystem = new FileSystemImpl();
        fileSystem.touch("hola.txt");
        fileSystem.touch("a.txt");
        fileSystem.mkdir("mi-directorio-dir");
        System.out.println("------------------------------------");
        fileSystem.ls("--ord=asc");
        System.out.println("------------------------------------");
        fileSystem.ls("--ord=desc");
        System.out.println("------------------------------------");
        fileSystem.ls("");
    }
}
