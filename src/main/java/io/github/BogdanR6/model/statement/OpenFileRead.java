package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.exception.FileOpeningException;
import io.github.BogdanR6.model.exception.InvalidTypeException;
import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.expression.Expression;
import io.github.BogdanR6.model.state.ProgramState;
import io.github.BogdanR6.model.type.Type;
import io.github.BogdanR6.model.value.StringValue;
import io.github.BogdanR6.model.value.Value;

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
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }
}
