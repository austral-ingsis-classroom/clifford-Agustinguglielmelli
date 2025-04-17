package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.filesystem.FileSystemImpl;


public class Main {
    public static void main(String[] args) {
        FileSystemImpl fileSystem = new FileSystemImpl();
        fileSystem.touch("hola");
        fileSystem.touch("a");
    }
}
