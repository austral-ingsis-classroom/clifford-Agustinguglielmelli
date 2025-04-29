package edu.austral.ingsis.clifford.auxclasses;

public class NameValidator {
  public static boolean isNameInvalid(String name) {
    if (name.contains("/") || name.contains(" ")) return true;
    return false;
  }
}
