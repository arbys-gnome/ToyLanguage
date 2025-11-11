package me.rares.repository;

import me.rares.model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements Repository {
    private List<ProgramState> programStates;
    private String logFilePath;
    private int currentProgramIndex;

    public MemoryRepository(ProgramState state, String logFilePath) {
        // single threaded implementation
        this.programStates = new ArrayList<>();
        this.programStates.add(state);
        this.logFilePath = logFilePath;
        this.currentProgramIndex = 0;
    }

    @Override
    public ProgramState getCurrentProgramState() {
        return programStates.get(currentProgramIndex);
    }

    @Override
    public void logProgramState() throws Exception {
        // A3: Write ExeStack, SymTable, Out, FileTable to file
        var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write("Program State\n");
        logFile.write(programStates.toString());
    }
}
