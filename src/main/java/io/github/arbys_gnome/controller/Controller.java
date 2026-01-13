package io.github.arbys_gnome.controller;

import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.repository.Repository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final Repository repository;
    private ExecutorService executor;

    public Controller(Repository repo) {
        repository = repo;
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> programs) {
        return programs.stream().filter(p -> !p.isFinished()).toList();
    }

    public void allStep() {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> programs = removeCompletedPrograms(repository.getPrograms());
        while (!programs.isEmpty()) {
            // TODO: add garbage collection
            oneStepForAllPrograms(programs);
            //remove the completed programs
            programs = removeCompletedPrograms(repository.getPrograms());
        }
        executor.shutdownNow();
        repository.setPrograms(programs);
    }

    public void oneStepForAllPrograms(List<ProgramState> programs) {
        programs.forEach(prg -> {
            try {
                repository.logProgramState(prg);
            } catch (Exception e) {
                System.err.println("Logging error: " + e.getMessage());
            }
        });

        List<Callable<ProgramState>> callList = programs.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .toList();

        try {
            // Execute concurrently
            List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            // statement execution exception
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            // 4. Add new threads
            programs.addAll(newPrgList);

            // 5. Log after execution
            programs.forEach(prg -> {
                try {
                    repository.logProgramState(prg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            // 6. Save programs in repository
            repository.setPrograms(programs);

        } catch (InterruptedException e) {
            throw new RuntimeException("Execution interrupted");
        }
    }
}
