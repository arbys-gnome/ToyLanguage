package io.github.BogdanR6.model.statement;

import io.github.BogdanR6.model.exception.InvalidTypeException;
import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.expression.Expression;
import io.github.BogdanR6.model.state.ProgramState;
import io.github.BogdanR6.model.type.Type;
import io.github.BogdanR6.model.value.StringValue;
import io.github.BogdanR6.model.value.Value;

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
    public ProgramState execute(ProgramState state) throws Exception {
        // Evaluate the expression
        Value value = expression.evaluate(state.symbolTable(), state.heap());

        // Check if the value is a StringValue
        if (!value.type().equals(Type.STRING)) {
            throw new InvalidTypeException("CloseFileRead: Expression must evaluate to a string type");
        }

        StringValue stringValue = (StringValue) value;
        String filename = stringValue.value();

        // Check if file is open in FileTable
        if (!state.fileTable().isDefined(stringValue)) {
            throw new InvalidVariableNameException("CloseFileRead: File '" + filename + "' is not open");
        }

        BufferedReader reader = state.fileTable().lookup(stringValue);

        try {
            // Close the file
            reader.close();

            // Remove from FileTable
            state.fileTable().remove(stringValue);

        } catch (IOException e) {
            throw new Exception("CloseFileRead: IO error closing file '" + filename + "'", e);
        }

        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}
