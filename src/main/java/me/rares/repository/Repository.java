package me.rares.repository;

import me.rares.model.state.ProgramState;

public interface Repository {
    ProgramState getCurrentProgramState();
    void logProgramState() throws Exception;
}
