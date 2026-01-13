package io.github.arbys_gnome.repository;

import io.github.arbys_gnome.model.state.ProgramState;

import java.util.List;

public interface Repository {
    List<ProgramState> getPrograms();
    void setPrograms(List<ProgramState> newList);
    void logProgramState(ProgramState state) throws Exception;
}
