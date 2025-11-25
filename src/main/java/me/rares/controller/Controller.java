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
    public void execute() {
        var programState = repository.getCurrentProgramState();

        while (!programState.isFinished()) {
            if (displaFlag) {
                IO.println(programState);
            }

            // Log the new state
            try {
                repository.logProgramState();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            // Collect the garbage
            programState.garbageCollect();

            Statement statement = programState.nextStatement();
            try {
                statement.execute(programState);
            } catch (InvalidVariableNameException | InvalidHeapAddressException | InvalidVariableTypeException e) {
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
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            return state;
        } catch (InvalidVariableNameException e) {
            throw new Exception("Variable error during execution: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unexpected error during execution: " + e.getMessage());
        } catch (InvalidVariableTypeException | InvalidHeapAddressException e) {
            throw new RuntimeException(e);
        }
    }
}
