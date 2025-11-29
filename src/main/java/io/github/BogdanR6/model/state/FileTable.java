package io.github.BogdanR6.model.state;

import io.github.BogdanR6.model.exception.FileOpeningException;
import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Collection;

public interface FileTable {
    void put(StringValue filename, BufferedReader reader);

    BufferedReader lookup(StringValue filename) throws FileOpeningException;

    boolean isDefined(StringValue filename);

    void remove(StringValue filename) throws InvalidVariableNameException;

    Collection<StringValue> getAllFilenames();

    boolean isEmpty();

    int size();

    String toString();

    void clear();
}
