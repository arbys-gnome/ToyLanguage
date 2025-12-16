package io.github.arbys_gnome.repository;

import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.statement.Statement;
import io.github.arbys_gnome.model.value.StringValue;
import io.github.arbys_gnome.model.value.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class MemoryRepository implements Repository {
    private final List<ProgramState> programStates;
    private final String logFilePath;
    private final int currentProgramIndex;

    public MemoryRepository(ProgramState state, String logFilePath) {
        this.programStates = new java.util.ArrayList<>();
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
        ProgramState state = getCurrentProgramState();

        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {

            logFile.println("ExeStack:");
            for (Statement stmt : state.executionStack()) {
                logFile.println(stmt.toString());
            }
            logFile.println();

            logFile.println("SymTable:");
            for (Map.Entry<String, Value> entry : state.symbolTable().entrySet()) {
                logFile.println(entry.getKey() + " --> " + entry.getValue());
            }
            logFile.println();

            logFile.println("Out:");
            for (Object value : state.output()) {
                logFile.println(value);
            }
            logFile.println();

            logFile.println("FileTable:");
            for (StringValue filename : state.fileTable().getAllFilenames()) {
                logFile.println(filename.value());
            }

            logFile.println("--------------------------------------------------");

        } catch (Exception e) {
            throw new Exception("Error writing program state to log file: " + e.getMessage());
        }
    }
}
