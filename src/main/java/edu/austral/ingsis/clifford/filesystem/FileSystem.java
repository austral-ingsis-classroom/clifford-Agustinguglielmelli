package edu.austral.ingsis.clifford.filesystem;

public interface FileSystem {

    public void ls(String flag);

    public void cd(String path);

    public void touch(String itemName);

    public void mkdir(String itemName);

    public void rm(String itemName);

    public void pwd();


}
