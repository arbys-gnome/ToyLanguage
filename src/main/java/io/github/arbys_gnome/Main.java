package io.github.arbys_gnome;

import io.github.arbys_gnome.controller.Controller;
import io.github.arbys_gnome.examples.Examples;
import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.state.*;
import io.github.arbys_gnome.model.statement.Statement;
import io.github.arbys_gnome.view.CLI;
import io.github.arbys_gnome.view.ExitCommand;
import io.github.arbys_gnome.view.RunExample;
import io.github.arbys_gnome.repository.MemoryRepository;
import io.github.arbys_gnome.repository.Repository;

import java.util.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * Main class (Interpreter) that sets up and runs the toy language interpreter
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private static class ProgramExample {
        String key;
        String description;
        Statement program;
        String logFile;

        public ProgramExample(String key, Statement program, String logFile) {
            this.key = key;
            this.description = program.toString();
            this.program = program;
            this.logFile = logFile;
        }
    }

    public static void main(String[] args) {
        // Define all example programs with their keys and log files
        List<ProgramExample> examples = Arrays.asList(
                new ProgramExample("1", Examples.ex1, "/tmp/log1.txt"),
                new ProgramExample("2", Examples.ex2, "/tmp/log2.txt"),
                new ProgramExample("3", Examples.ex3, "/tmp/log3.txt"),
                new ProgramExample("4", Examples.ex4, "/tmp/log4.txt"),
                new ProgramExample("5", Examples.ex5, "/tmp/log5.txt")
        );

        // List to store successfully type-checked controllers
        List<ProgramExample> validExamples = new ArrayList<>();
        Map<ProgramExample, Controller> controllers = new HashMap<>();

        // Type check each example and create controllers for valid ones
        for (ProgramExample example : examples) {
            try {
                // Perform type checking
                example.program.typecheck(new HashMap<>());

                // Create program state
                ProgramState programState = new ProgramState(
                        new ListExecutionStack(),
                        new MapSymbolTable(),
                        new MapFileTable(),
                        new MapHeap(),
                        new ListOutput(),
                        example.program
                );

                // Create repository and controller
                Repository repo = new MemoryRepository(programState, example.logFile);
                Controller controller = new Controller(repo, false);

                // Store the valid example and its controller
                validExamples.add(example);
                controllers.put(example, controller);

                logger.info("Example {} passed type check and was loaded successfully.", example.key);

            } catch (InvalidTypeException e) {
                logger.error("Example {} failed type check: {}", example.key, e.getMessage());
            } catch (Exception e) {
                logger.error("Example {} failed to load: {}", example.key, e.getMessage());
            }
        }

        CLI menu = new CLI();
        menu.addCommand(new ExitCommand("0", "Exit"));

        // Add only valid examples to the menu
        for (ProgramExample example : validExamples) {
            Controller controller = controllers.get(example);
            menu.addCommand(new RunExample(example.key, example.description, controller));
        }

        // Log summary
        logger.info("Loaded {} out of {} examples.", validExamples.size(), examples.size());

        // Show the menu - this will run until user selects exit
        menu.show();
    }
}
