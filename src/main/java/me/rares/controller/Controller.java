package me.rares.controller;

import me.rares.model.state.ListExecutionStack;
import me.rares.model.state.ListOutput;
import me.rares.model.state.MapSymbolTable;
import me.rares.model.state.ProgramState;
import me.rares.model.statement.Statement;

public class Controller {

    // complete execution
    public void execute(Statement program) {
        ListExecutionStack executionStack = new ListExecutionStack();
        executionStack.push(program);

        var programState = new ProgramState(
                executionStack,
                new MapSymbolTable(),
                new ListOutput()
        );

        while (!programState.isFinished()) {
            Statement statement = programState.nextStatement();
            statement.execute(programState);
        }

        IO.println(programState);
    }

    public ProgramState oneStep(ProgramState state) {
        // TODO: Implement
        return state;
    }
}
