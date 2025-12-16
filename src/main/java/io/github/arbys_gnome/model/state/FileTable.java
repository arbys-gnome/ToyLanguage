package io.github.arbys_gnome.model.state;

import io.github.arbys_gnome.model.exception.FileOpeningException;
import io.github.arbys_gnome.model.exception.InvalidVariableNameException;
import io.github.arbys_gnome.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Collection;

public interface FileTable {

    void put(StringValue filename, BufferedReader reader);

    BufferedReader getReader(StringValue filename) throws FileOpeningException;

    boolean isDefined(StringValue filename);

    void remove(StringValue filename) throws InvalidVariableNameException;

    Collection<StringValue> getAllFilenames();

    boolean isEmpty();

    int size();

    String toString();

    void clear();
}
