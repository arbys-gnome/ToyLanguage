package io.github.arbys_gnome.model.state;

import io.github.arbys_gnome.model.exception.InvalidVariableNameException;
import io.github.arbys_gnome.model.exception.InvalidVariableTypeException;
import io.github.arbys_gnome.model.exception.UnallocatedAddressException;
import io.github.arbys_gnome.model.statement.Statement;
import io.github.arbys_gnome.model.value.RefValue;
import io.github.arbys_gnome.model.value.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProgramState {
    private static Integer lastId = 0;
    private final Integer id;
    private ExecutionStack executionStack;
    private SymbolTable symbolTable;
    private FileTable fileTable;
    private Heap heap;
    private Output output;
    private Statement originalStatement;

    private static synchronized int getNextId() {
        return ++lastId;
    }

    public ProgramState(ExecutionStack executionStack,
                        SymbolTable symbolTable,
                        FileTable fileTable,
                        Heap heap,
                        Output output,
                        Statement statement
    ) {
        this.id = getNextId();
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalStatement = deepCopy(statement);
        this.output = output;

        // Push the program onto the execution stack
        this.executionStack.push(statement);
    }

    private Statement deepCopy(Statement stmt) {
        return stmt;
    }

    public void clean() {
        this.executionStack.clear();
        this.symbolTable.clear();
        this.fileTable.clear();
        this.heap.clear();
        this.output.clear();
    }

    public boolean isFinished() {
        return executionStack.isEmpty();
    }

    public Statement nextStatement() {
        return executionStack.pop();
    }

    public ExecutionStack executionStack() {
        return executionStack;
    }

    public SymbolTable symbolTable() {
        return symbolTable;
    }

    public Heap heap() {
        return heap;
    }

    public Output output() {
        return output;
    }

    public FileTable fileTable() {
        return fileTable;
    }

    public Statement program() {
        return originalStatement;
    }

    public void setExecutionStack(ExecutionStack executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setHeap(Heap heap) {
        this.heap = heap;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public void setFileTable(FileTable fileTable) {
        this.fileTable = fileTable;
    }

    public void setOriginalStatement(Statement originalStatement) {
        this.originalStatement = originalStatement;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Program ").append(id).append(":\n");

        sb.append(executionStack.toString()).append("\n");

        sb.append(symbolTable.toString()).append("\n");

        sb.append(fileTable.toString()).append("\n");

        sb.append(heap.toString()).append("\n");

        sb.append(output.toString()).append("\n");

        return sb.toString();
    }

    private Set<Integer> computeReachableAddresses(List<Integer> rootAddresses) {
        Set<Integer> reachable = new HashSet<>(rootAddresses);
        boolean newAddressFound;

        do {
            newAddressFound = false;

            for (var entry : heap.entrySet()) {
                Integer address = entry.getKey();
                Value value = entry.getValue();

                // If this address is already known to be reachable
                if (reachable.contains(address) && value.type().isReference()) {

                    int innerAddress = ((RefValue) value).address();

                    // If we found a new reachable address, add it and re-run
                    if (reachable.add(innerAddress)) {
                        newAddressFound = true;
                    }
                }
            }
        } while (newAddressFound);

        return reachable;
    }

    public void garbageCollect() {
        List<Integer> rootAddresses =
                StreamSupport.stream(symbolTable.entrySet().spliterator(), false)
                        .map(Map.Entry::getValue)
                        .filter(value -> value.type().isReference())
                        .map(value -> ((RefValue) value).address())
                        .toList();

        Set<Integer> reachableAddresses = computeReachableAddresses(rootAddresses);

        Map<Integer, Value> filteredHeapContent =
                StreamSupport.stream(heap.entrySet().spliterator(), false)
                        .filter(entry -> reachableAddresses.contains(entry.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        heap.setContent(filteredHeapContent);
    }

    public void garbageCollectUnsafe() {
        List<Integer> rootAddresses =
                StreamSupport.stream(symbolTable.entrySet().spliterator(), false)
                        .map(Map.Entry::getValue)
                        .filter(value -> value.type().isReference())
                        .map(value -> ((RefValue) value).address())
                        .toList();

        Map<Integer, Value> filteredHeapContent =
                StreamSupport.stream(heap.entrySet().spliterator(), false)
                        .filter(entry -> rootAddresses.contains(entry.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        heap.setContent(filteredHeapContent);
    }

    /**
     * Executes a single step of the program.
     * Retrieves the next statement from the execution stack
     * and executes it.
     */
    public ProgramState oneStep() throws Exception {
        if (this.isFinished()) {
            throw new Exception("Program execution stack is empty.");
        }

        // Get the next statement from the stack
        Statement currentStatement = nextStatement();

        try {
            // Execute the statement and update the program state
            currentStatement.execute(this);

            // Collect the garbage
            garbageCollect();

            return this;
        } catch (InvalidVariableNameException | InvalidVariableTypeException | UnallocatedAddressException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Unexpected error during execution: " + e.getMessage());
        }
    }
}
