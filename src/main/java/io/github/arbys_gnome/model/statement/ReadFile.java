package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.FileOpeningException;
import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.exception.InvalidValueException;
import io.github.arbys_gnome.model.exception.InvalidVariableNameException;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.IntValue;
import io.github.arbys_gnome.model.value.StringValue;
import io.github.arbys_gnome.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

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
    public ProgramState execute(ProgramState state) throws Exception {
        // Check if variable is defined and is int type
        if (!state.symbolTable().isDefined(variableName)) {
            throw new InvalidVariableNameException("ReadFile: Variable '" + variableName + "' is not defined");
        }

        Value varValue = state.symbolTable().getValue(variableName);
        if (!varValue.type().equals(Type.INT)) {
            throw new InvalidTypeException("ReadFile: Variable '" + variableName + "' must be of int type");
        }

        // Evaluate expression to get filename
        Value value = expression.evaluate(state.symbolTable(), state.heap());

        if (!value.type().equals(Type.STRING)) {
            throw new InvalidTypeException("ReadFile: Expression must evaluate to a string type");
        }

        StringValue stringValue = (StringValue) value;
        String filename = stringValue.value();

        // Check if file is open in FileTable
        if (!state.fileTable().isDefined(stringValue)) {
            throw new FileOpeningException("ReadFile: File '" + filename + "' is not open");
        }

        // Get BufferedReader from FileTable
        BufferedReader reader = state.fileTable().getReader(stringValue);

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
                    throw new InvalidValueException("ReadFile: Line '" + line + "' is not a valid integer");
                }
            }

            // Update SymTable
            state.symbolTable().setValue(variableName, intValue);

        } catch (IOException e) {
            throw new Exception("ReadFile: IO error reading from file '" + filename + "'", e);
        }

        return state;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        if (!typeEnv.containsKey(variableName)) {
            throw new InvalidTypeException("Variable " + variableName + " is not defined");
        }
        Type varType = typeEnv.get(variableName);
        if (!varType.equals(Type.INT)) {
            throw new InvalidTypeException("ReadFile: Variable must be of type int");
        }

        Type typexp = null;
        try {
            typexp = expression.typecheck(typeEnv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!typexp.equals(Type.STRING)) {
            throw new InvalidTypeException("ReadFile: Expression must be of type string");
        }

        return typeEnv;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + variableName + ")";
    }
}
