package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.FileOpeningException;
import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.exception.InvalidVariableNameException;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.StringValue;
import io.github.arbys_gnome.model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Statement: openRFile(exp)
 * Opens a file for reading and adds it to the FileTable
 */
public class OpenFileRead implements Statement {
    private Expression expression;

    public OpenFileRead(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        // Evaluate the expression
        Value value = expression.evaluate(state.symbolTable(), state.heap());

        // Check if the value is a StringValue
        if (!(value.type().equals(Type.STRING))) {
            throw new InvalidTypeException("OpenFileRead: Expression must evaluate to a string type");
        }

        StringValue stringValue = (StringValue) value;
        String filename = stringValue.value();

        // Check if file is already open
        if (state.fileTable().isDefined(stringValue)) {
            throw new FileOpeningException("OpenFileRead: File '" + filename + "' is already open");
        }

        // Try to open the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // Add to FileTable
            state.fileTable().put(stringValue, reader);

        } catch (FileNotFoundException e) {
            throw new InvalidVariableNameException("OpenFileRead: File '" + filename + "' not found");
        }

        return state;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        Type expType = null;
        try {
            expType = expression.typecheck(typeEnv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (expType.equals(Type.STRING)) {
            return typeEnv;
        } else {
            throw new InvalidTypeException("OpenFileRead: Expression must be of type string");
        }
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }
}
