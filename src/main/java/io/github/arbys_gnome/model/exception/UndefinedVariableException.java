package io.github.arbys_gnome.model.exception;

public class UndefinedVariableException extends Exception {
    public UndefinedVariableException(String undefinedVariableName) {
        super("Variable " + undefinedVariableName + " is not defined.");
    }
}
