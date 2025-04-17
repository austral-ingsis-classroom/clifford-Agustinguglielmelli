package edu.austral.ingsis.clifford.filesystem;

public interface FileSystem {

    public void ls(String flag);

    public void cd();

    public void touch(String itemName);

    public void mkdir(String itemName);

    public void rm();

    public void pwd();


}
