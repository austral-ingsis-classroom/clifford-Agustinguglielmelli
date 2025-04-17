package edu.austral.ingsis.clifford.filesystem;

public interface FileSystem {

    public void ls();

    public void cd();

    public void touch(String itemName);

    public void mkdir();

    public void rm();

    public void pwd();


}
