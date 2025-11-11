package me.rares.model.statement;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.type.Type;
import me.rares.model.value.StringValue;
import me.rares.model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Statement: openRFile(exp)
 * Opens a file for reading and adds it to the FileTable
 */
public class OpenFileRead implements Statement {
    private Expression expression;

    public OpenFileRead(Expression expression) {
        this.expression = expression;
    }

    /**
     * Execution steps:
     * 1. Evaluate the expression
     * 2. Check if it's a StringValue
     * 3. Check if the file is not already open (not in FileTable)
     * 4. Open the file using BufferedReader
     * 5. Add entry to FileTable
     */
    @Override
    public ProgramState execute(ProgramState state) {
        // Evaluate the expression
        Value value = expression.evaluate(state.getSymbolTable());

        // Check if the value is a StringValue
        if (!value.getType().equals(Type.STRING)) {
            throw new InvalidTypeException("OpenFileRead: Expression must evaluate to a string type");
        }

        StringValue stringValue = (StringValue) value;
        String filename = stringValue.value();

        // Check if file is already open
        if (state.getFileTable().isDefined(stringValue)) {
            throw new RuntimeException("OpenFileRead: File '" + filename + "' is already open");
        }

        // Try to open the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // Add to FileTable
            state.getFileTable().put(stringValue, reader);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("OpenFileRead: File '" + filename + "' not found", e);
        }

        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }
}
