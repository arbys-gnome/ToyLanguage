package me.rares.controller;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.exception.InvalidVariableTypeException;
import me.rares.model.state.ProgramState;
import me.rares.model.statement.Statement;
import me.rares.repository.Repository;

public class Controller {
    private final Repository repository;
    private boolean displaFlag = false;

    public Controller(Repository repo, boolean displayFlag) {
        repository = repo;
        displaFlag = displayFlag;
    }

    // complete execution
    public void execute() throws Exception {
        var state = repository.getCurrentProgramState();

        while (!state.isFinished()) {
            if (displaFlag) {
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
            } catch (InvalidVariableNameException | InvalidHeapAddressException | InvalidVariableTypeException e) {
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
        } catch (InvalidVariableNameException | InvalidVariableTypeException | InvalidHeapAddressException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Unexpected error during execution: " + e.getMessage());
        }
    }
}
