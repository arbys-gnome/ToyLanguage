package io.github.BogdanR6.model.exception;

public class UndefinedVariableException extends Exception {
    public UndefinedVariableException(String undefinedVariableName) {
        super("Variable " + undefinedVariableName + " is not defined.");
    }
}
