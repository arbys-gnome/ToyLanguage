package io.github.BogdanR6.model.state;

import io.github.BogdanR6.model.exception.FileOpeningException;
import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapFileTable implements FileTable {
    private Map<StringValue, BufferedReader> fileTable;

    public MapFileTable() {
        this.fileTable = new HashMap<>();
    }

    @Override
    public void put(StringValue filename, BufferedReader reader) {
        fileTable.put(filename, reader);
    }

    @Override
    public BufferedReader lookup(StringValue filename) throws FileOpeningException {
        if (!fileTable.containsKey(filename)) {
            throw new FileOpeningException("FileTable: File '" + filename.value() + "' is not open");
        }
        return fileTable.get(filename);
    }

    @Override
    public boolean isDefined(StringValue filename) {
        return fileTable.containsKey(filename);
    }

    @Override
    public void remove(StringValue filename) throws InvalidVariableNameException {
        if (!fileTable.containsKey(filename)) {
            throw new InvalidVariableNameException("FileTable: Cannot remove file '" + filename.value() + "' - not defined");
        }
        fileTable.remove(filename);
    }

    @Override
    public Collection<StringValue> getAllFilenames() {
        return fileTable.keySet();
    }

    @Override
    public boolean isEmpty() {
        return fileTable.isEmpty();
    }

    @Override
    public int size() {
        return fileTable.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MapFileTable:");
        if (fileTable.isEmpty()) {
            return sb.append("\n(empty)").toString();
        }

        for (StringValue filename : fileTable.keySet()) {
            sb.append("\n").append(filename.value());
        }
        return sb.toString();
    }

    @Override
    public void clear() {
        fileTable.clear();
    }
}
