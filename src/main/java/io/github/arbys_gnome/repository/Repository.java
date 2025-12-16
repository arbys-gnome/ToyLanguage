package io.github.arbys_gnome.repository;

import io.github.arbys_gnome.model.state.ProgramState;

public interface Repository {
    ProgramState getCurrentProgramState();
    void logProgramState() throws Exception;
}
