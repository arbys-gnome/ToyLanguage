package me.rares.controller;

import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.state.ListExecutionStack;
import me.rares.model.state.ListOutput;
import me.rares.model.state.MapSymbolTable;
import me.rares.model.state.ProgramState;
import me.rares.model.statement.Statement;
import me.rares.repository.Repository;

public class Controller {
    private Repository repository;

    public Controller(Repository repo) {
        repository = repo;
    }

    // complete execution
    public void execute() {
        var programState = repository.getCurrentProgramState();

        while (!programState.isFinished()) {
            Statement statement = programState.nextStatement();
            try {
                statement.execute(programState);
            } catch (InvalidVariableNameException e) {
                throw new RuntimeException(e);
            }
        }

        IO.println(programState);
    }

    /**
     * Executes a single step of the program.
     * Retrieves the next statement from the execution stack,
     * executes it, and logs the resulting program state.
     */
    public ProgramState oneStep(ProgramState state) throws Exception {
        if (state.getExecutionStack().isEmpty()) {
            throw new Exception("Program execution stack is empty.");
        }

        // Get the next statement from the stack
        Statement currentStatement = state.nextStatement();

        try {
            // Execute the statement and update the program state
            currentStatement.execute(state);

            // Log the new state after execution
            repository.logProgramState();

            return state;
        } catch (InvalidVariableNameException e) {
            throw new Exception("Variable error during execution: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unexpected error during execution: " + e.getMessage());
        }
    }
}
