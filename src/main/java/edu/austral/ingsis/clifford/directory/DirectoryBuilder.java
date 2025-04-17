package edu.austral.ingsis.clifford.directory;

public class DirectoryBuilder {
    private String name;
    private String path;


    public DirectoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DirectoryBuilder setPath(String path) {
        this.path = path;
        return this;
    }



    public Directory build() {
        return new Directory(name, path);
    }
}
