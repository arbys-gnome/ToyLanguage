package io.github.BogdanR6.repository;

import io.github.BogdanR6.model.state.ProgramState;

public interface Repository {
    ProgramState getCurrentProgramState();
    void logProgramState() throws Exception;
}
