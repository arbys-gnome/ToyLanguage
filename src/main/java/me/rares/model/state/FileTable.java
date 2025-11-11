package me.rares.model.state;

import me.rares.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Collection;

/**
 * Interface for the File Table ADT.
 * Maps StringValue (filename) to BufferedReader (file descriptor).
 * This is a dictionary-like ADT similar to SymbolTable.
 */
public interface FileTable {

    /**
     * Add a new file entry to the table
     * @param filename The filename as a StringValue
     * @param reader The BufferedReader for the file
     */
    void put(StringValue filename, BufferedReader reader);

    /**
     * Get the BufferedReader associated with a filename
     * @param filename The filename to lookup
     * @return The BufferedReader associated with the filename
     * @throws RuntimeException if filename is not in the table
     */
    BufferedReader lookup(StringValue filename);

    /**
     * Check if a filename is in the table
     * @param filename The filename to check
     * @return true if the filename exists, false otherwise
     */
    boolean isDefined(StringValue filename);

    /**
     * Remove a file entry from the table
     * @param filename The filename to remove
     */
    void remove(StringValue filename);

    /**
     * Get all filenames currently in the table
     * @return Collection of all StringValue keys
     */
    Collection<StringValue> getAllFilenames();

    /**
     * Check if the table is empty
     * @return true if empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Get the number of open files
     * @return The size of the table
     */
    int size();

    /**
     * String representation of the file table
     */
    String toString();
}
