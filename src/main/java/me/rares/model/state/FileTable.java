package me.rares.model.state;

import me.rares.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Collection;

public interface FileTable {
    void put(StringValue filename, BufferedReader reader);

    BufferedReader lookup(StringValue filename);

    boolean isDefined(StringValue filename);

    void remove(StringValue filename);

    Collection<StringValue> getAllFilenames();

    boolean isEmpty();

    int size();

    String toString();

    void clear();
}
