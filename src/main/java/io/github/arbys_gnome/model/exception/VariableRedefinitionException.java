package io.github.arbys_gnome.model.exception;

public class VariableRedefinitionException extends Exception {
    public VariableRedefinitionException(String variableName) {
        super("Variable " + variableName + " is already defined");
    }
}
