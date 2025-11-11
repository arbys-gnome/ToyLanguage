package me.rares.model.state;

import me.rares.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of FileTable using a HashMap.
 * Wraps Java's HashMap to provide file table functionality.
 */
public class MapFileTable implements FileTable {
    private Map<StringValue, BufferedReader> fileTable;

    /**
     * Constructor - initializes an empty file table
     */
    public MapFileTable() {
        this.fileTable = new HashMap<>();
    }

    @Override
    public void put(StringValue filename, BufferedReader reader) {
        fileTable.put(filename, reader);
    }

    @Override
    public BufferedReader lookup(StringValue filename) throws RuntimeException {
        if (!fileTable.containsKey(filename)) {
            throw new RuntimeException("FileTable: File '" + filename.value() + "' is not open");
        }
        return fileTable.get(filename);
    }

    @Override
    public boolean isDefined(StringValue filename) {
        return fileTable.containsKey(filename);
    }

    @Override
    public void remove(StringValue filename) {
        if (!fileTable.containsKey(filename)) {
            throw new RuntimeException("FileTable: Cannot remove file '" + filename.value() + "' - not in table");
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
}
