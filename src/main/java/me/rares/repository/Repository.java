package me.rares.repository;

import me.rares.model.state.ProgramState;

public interface Repository {
    ProgramState getCurrentProgramState();
    // Log file format must include: ExeStack, SymTable, Out, FileTable sections
    void logProgramState() throws Exception;
}
