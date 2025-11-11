package me.rares.model.state;

import me.rares.model.statement.Statement;

/**
 * Represents the complete state of a program during execution.
 * Includes factory methods for convenient creation.
 */
public class ProgramState {
    private ExecutionStack executionStack;
    private SymbolTable symbolTable;
    private Output output;
    private FileTable fileTable;
    private Statement program;

    /**
     * Full constructor - use when you want to provide all components
     */
    public ProgramState(ExecutionStack executionStack,
                        SymbolTable symbolTable,
                        Output output,
                        FileTable fileTable,
                        Statement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.program = deepCopy(program);

        // Push the original program onto the execution stack
        this.executionStack.push(program);
    }

    /**
     * Factory method - creates ProgramState with multiple custom components.
     * Any component can be null, and will be created as empty.
     *
     * Usage:
     *   ProgramState state = ProgramState.builder()
     *       .executionStack(myStack)  // optional
     *       .symbolTable(mySymTable)  // optional
     *       .output(myOutput)         // optional
     *       .fileTable(myFileTable)   // optional
     *       .program(myProgram)       // required
     *       .build();
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for flexible ProgramState creation
     */
    public static class Builder {
        private ExecutionStack executionStack;
        private SymbolTable symbolTable;
        private Output output;
        private FileTable fileTable;
        private Statement program;

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
                    output != null ? output : new ListOutput(),
                    fileTable != null ? fileTable : new MapFileTable(),
                    program
            );
        }
    }

    /**
     * Deep copy the original program (optional implementation)
     */
    private Statement deepCopy(Statement stmt) {
        // For now, statements are treated as immutable
        return stmt;
    }

    /**
     * Check if program execution is finished
     */
    public boolean isFinished() {
        return executionStack.isEmpty();
    }

    /**
     * Get and remove the next statement from the execution stack
     */
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

        sb.append(output.toString()).append("\n");

        sb.append(fileTable.toString()).append("\n");

        return sb.toString();
    }
}
