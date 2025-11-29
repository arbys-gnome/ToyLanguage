package io.github.BogdanR6.controller;

import io.github.BogdanR6.model.exception.UnallocatedAddressException;
import io.github.BogdanR6.model.exception.InvalidVariableNameException;
import io.github.BogdanR6.model.exception.InvalidVariableTypeException;
import io.github.BogdanR6.model.state.ProgramState;
import io.github.BogdanR6.model.statement.Statement;
import io.github.BogdanR6.repository.Repository;

public class Controller {
    private final Repository repository;
    private boolean displayFlag = false;

    public Controller(Repository repo, boolean displayFlag) {
        repository = repo;
        this.displayFlag = displayFlag;
    }

    // complete execution
    public void execute() throws Exception {
        var state = repository.getCurrentProgramState();

        while (!state.isFinished()) {
            if (displayFlag) {
                IO.println(state);
            }

            // Log the new state
            try {
                repository.logProgramState();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Collect the garbage
            state.garbageCollect();

            Statement statement = state.nextStatement();
            try {
                statement.execute(state);
            } catch (InvalidVariableNameException | UnallocatedAddressException | InvalidVariableTypeException e) {
                throw e;
            } catch (Exception e) {
                throw new Exception("Unexpected error during execution", e);
            }
        }

        IO.println(state);
    }

    /**
     * Executes a single step of the program.
     * Retrieves the next statement from the execution stack,
     * executes it, and logs the resulting program state.
     */
    public ProgramState oneStep(ProgramState state) throws Exception {
        if (state.executionStack().isEmpty()) {
            throw new Exception("Program execution stack is empty.");
        }

        // Get the next statement from the stack
        Statement currentStatement = state.nextStatement();

        try {
            // Execute the statement and update the program state
            currentStatement.execute(state);

            // Log the new state after each step
            try {
                repository.logProgramState();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Collect the garbage
            state.garbageCollect();

            return state;
        } catch (InvalidVariableNameException | InvalidVariableTypeException | UnallocatedAddressException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Unexpected error during execution: " + e.getMessage());
        }
    }
}
