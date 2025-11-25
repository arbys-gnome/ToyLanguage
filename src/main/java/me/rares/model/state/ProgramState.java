package me.rares.model.state;

import me.rares.model.statement.Statement;
import me.rares.model.value.RefValue;
import me.rares.model.value.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProgramState {
    private ExecutionStack executionStack;
    private SymbolTable symbolTable;
    private FileTable fileTable;
    private Heap heap;
    private Output output;
    private Statement originalStatement;

    public ProgramState(ExecutionStack executionStack,
                        SymbolTable symbolTable,
                        FileTable fileTable,
                        Heap heap,
                        Output output,
                        Statement statement
    ) {
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
}
