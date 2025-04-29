package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.filesystem.Directory;
import edu.austral.ingsis.clifford.filesystem.FileSystemItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ls implements Command {
  String order;

  public Ls(String order) {
    this.order = order;
  }

  public Ls() {
    this.order = "";
  }

  @Override
  public CommandResult execute(Directory directory) {
    List<FileSystemItem> items = directory.getChildrenValues();

    if (items.isEmpty()) {
      return CommandResult.noRootChange(directory, "");
    }

    List<FileSystemItem> sortedItems = sortItems(items);
    String result = getNames(sortedItems);

    return CommandResult.noRootChange(directory, result);
  }

  private List<FileSystemItem> sortItems(List<FileSystemItem> items) {
    List<FileSystemItem> sortedItems = new ArrayList<>(items);

    if (order.equals("asc")) {
      sortedItems.sort(Comparator.comparing(FileSystemItem::getName));
    } else if (order.equals("desc")) {
      sortedItems.sort(Comparator.comparing(FileSystemItem::getName).reversed());
    }
    return sortedItems;
  }

  private String getNames(List<FileSystemItem> sortedItems) {
    List<String> names = new ArrayList<>();
    for (FileSystemItem item : sortedItems) {
      names.add(item.getName());
    }

    return String.join(" ", names);
  }
}
