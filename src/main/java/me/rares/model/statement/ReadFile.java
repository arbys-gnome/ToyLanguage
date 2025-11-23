package me.rares.model.statement;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.type.Type;
import me.rares.model.value.IntValue;
import me.rares.model.value.StringValue;
import me.rares.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Statement: readFile(exp, var_name)
 * Reads a line from a file and stores the integer value in a variable
 */
public class ReadFile implements Statement {
    private Expression expression;
    private String variableName;

    public ReadFile(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    /**
     * Execution steps:
     * 1. Check if var_name is defined in SymTable and is int type
     * 2. Evaluate expression to get filename (must be string)
     * 3. Get BufferedReader from FileTable using the filename
     * 4. Read a line from the file
     * 5. If null, use 0; otherwise parse the integer
     * 6. Update SymTable with the new value
     */
    @Override
    public ProgramState execute(ProgramState state) throws InvalidVariableNameException {
        // Check if variable is defined and is int type
        if (!state.getSymbolTable().isDefined(variableName)) {
            throw new RuntimeException("ReadFile: Variable '" + variableName + "' is not defined");
        }

        Value varValue = state.getSymbolTable().lookup(variableName);
        if (!varValue.type().equals(Type.INT)) {
            throw new InvalidTypeException("ReadFile: Variable '" + variableName + "' must be of int type");
        }

        // Evaluate expression to get filename
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());

        if (!value.type().equals(Type.STRING)) {
            throw new InvalidTypeException("ReadFile: Expression must evaluate to a string type");
        }

        StringValue stringValue = (StringValue) value;
        String filename = stringValue.value();

        // Check if file is open in FileTable
        if (!state.getFileTable().isDefined(stringValue)) {
            throw new RuntimeException("ReadFile: File '" + filename + "' is not open");
        }

        // Get BufferedReader from FileTable
        BufferedReader reader = state.getFileTable().lookup(stringValue);

        try {
            // Read a line from the file
            String line = reader.readLine();

            IntValue intValue;
            if (line == null) {
                // End of file - use default value 0
                intValue = new IntValue(0);
            } else {
                // Parse the line as an integer
                try {
                    int parsedValue = Integer.parseInt(line.trim());
                    intValue = new IntValue(parsedValue);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("ReadFile: Line '" + line + "' is not a valid integer", e);
                }
            }

            // Update SymTable
            state.getSymbolTable().setValue(variableName, intValue);

        } catch (IOException e) {
            throw new RuntimeException("ReadFile: IO error reading from file '" + filename + "'", e);
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + variableName + ")";
    }
}
