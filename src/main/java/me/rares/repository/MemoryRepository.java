package me.rares.repository;

import me.rares.model.state.ProgramState;
import me.rares.model.statement.Statement;
import me.rares.model.value.StringValue;
import me.rares.model.value.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class MemoryRepository implements Repository {
    private final List<ProgramState> programStates;
    private final String logFilePath;
    private int currentProgramIndex;

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
            // Assuming your ExecutionStack supports iteration (top to bottom)
            // or has a method to get elements as a List
            for (Statement stmt : state.getExecutionStack()) {
                logFile.println(stmt.toString()); // Uses infix traversal representation
            }

            logFile.println("SymTable:");
            for (Map.Entry<String, Value> entry : state.getSymbolTable().entrySet()) {
                logFile.println(entry.getKey() + " --> " + entry.getValue());
            }

            logFile.println("Out:");
            for (Object value : state.getOutput()) {
                logFile.println(value);
            }

            logFile.println("FileTable:");
            for (StringValue filename : state.getFileTable().getAllFilenames()) {
                logFile.println(filename.value());
            }

            logFile.println("--------------------------------------------------");

        } catch (Exception e) {
            throw new Exception("Error writing program state to log file: " + e.getMessage());
        }
    }
}
