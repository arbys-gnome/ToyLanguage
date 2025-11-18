package me.rares.model.state;

import me.rares.model.statement.Statement;

/**
 * Represents the complete state of a program during execution.
 * Includes factory methods for convenient creation.
 */
public class ProgramState {
    private ExecutionStack executionStack;
    private SymbolTable symbolTable;
    private FileTable fileTable;
    private Heap heap;
    private Output output;
    private final Statement program;

    public ProgramState(ExecutionStack executionStack,
                        SymbolTable symbolTable,
                        FileTable fileTable,
                        Heap heap,
                        Statement program,
                        Output output
    ) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.fileTable = fileTable;
        this.heap = heap;
        this.program = deepCopy(program);
        this.output = output;

        // Push the program onto the execution stack
        this.executionStack.push(program);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ExecutionStack executionStack;
        private SymbolTable symbolTable;
        private FileTable fileTable;
        Heap heap;
        private Statement program;
        private Output output;

        public Builder executionStack(ExecutionStack executionStack) {
            this.executionStack = executionStack;
            return this;
        }

        public Builder symbolTable(SymbolTable symbolTable) {
            this.symbolTable = symbolTable;
            return this;
        }

        public Builder output(Output output) {
            this.output = output;
            return this;
        }

        public Builder fileTable(FileTable fileTable) {
            this.fileTable = fileTable;
            return this;
        }

        public Builder heap(Heap heap) {
            this.heap = heap;
            return this;
        }

        public Builder program(Statement program) {
            this.program = program;
            return this;
        }

        public ProgramState build() {
            if (program == null) {
                throw new IllegalStateException("Program cannot be null");
            }

            return new ProgramState(
                    executionStack != null ? executionStack : new ListExecutionStack(),
                    symbolTable != null ? symbolTable : new MapSymbolTable(),
                    fileTable != null ? fileTable : new MapFileTable(),
                    heap != null ? heap : new MapHeap(),
                    program,
                    output != null ? output : new ListOutput()
            );
        }
    }

    private Statement deepCopy(Statement stmt) {
        // For now, statements are treated as immutable
        return stmt;
    }

    public boolean isFinished() {
        return executionStack.isEmpty();
    }

    public Statement nextStatement() {
        return executionStack.pop();
    }

    // ============== GETTERS ==============
    public ExecutionStack getExecutionStack() {
        return executionStack;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public Heap getHeap() {
        return heap;
    }

    public Output getOutput() {
        return output;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public Statement getProgram() {
        return program;
    }

    // ============== SETTERS ==============
    public void setExecutionStack(ExecutionStack executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public void setFileTable(FileTable fileTable) {
        this.fileTable = fileTable;
    }

    // ============== TO STRING ==============
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
}
