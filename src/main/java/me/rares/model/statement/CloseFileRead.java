package me.rares.model.statement;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.type.Type;
import me.rares.model.value.StringValue;
import me.rares.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Statement: closeRFile(exp)
 * Closes an open file and removes it from the FileTable
 */
public class CloseFileRead implements Statement {
    private final Expression expression;

    public CloseFileRead(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        // Evaluate the expression
        Value value = expression.evaluate(state.getSymbolTable());

        // Check if the value is a StringValue
        if (!value.type().equals(Type.STRING)) {
            throw new InvalidTypeException("CloseFileRead: Expression must evaluate to a string type");
        }

        StringValue stringValue = (StringValue) value;
        String filename = stringValue.value();

        // Check if file is open in FileTable
        if (!state.getFileTable().isDefined(stringValue)) {
            throw new RuntimeException("CloseFileRead: File '" + filename + "' is not open");
        }

        BufferedReader reader = state.getFileTable().lookup(stringValue);

        try {
            // Close the file
            reader.close();

            // Remove from FileTable
            state.getFileTable().remove(stringValue);

        } catch (IOException e) {
            throw new RuntimeException("CloseFileRead: IO error closing file '" + filename + "'", e);
        }

        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}
