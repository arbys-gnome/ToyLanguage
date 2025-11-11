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

    public ProgramState oneStep(ProgramState state) {
        // TODO: Implement
        return state;
    }
}
