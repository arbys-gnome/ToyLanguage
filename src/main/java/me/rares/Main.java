package me.rares;

import me.rares.controller.Controller;
import me.rares.examples.Examples;
import me.rares.model.expression.*;
import me.rares.model.state.*;
import me.rares.model.statement.*;
import me.rares.model.value.*;
import me.rares.repository.MemoryRepository;
import me.rares.repository.Repository;
import me.rares.view.*;


/**
 * Main class (Interpreter) that sets up and runs the toy language interpreter
 */
public class Main {

    public static void main(String[] args) {

        ProgramState program1 = ProgramState.builder()
                                    .program(Examples.ex1)
                                    .build();

        // Create state and controller for example 1
        Repository repo1 = new MemoryRepository(program1, "/tmp/log1.txt");
        Controller ctr1 = new Controller(repo1);


        ProgramState program2 = ProgramState.builder().program(Examples.ex2).build();
        Repository repo2 = new MemoryRepository(program2, "/tmp/log2.txt");
        Controller ctr2 = new Controller(repo2);

        ProgramState program3 = ProgramState.builder().program(Examples.ex3).build();
        Repository repo3 = new MemoryRepository(program3, "/tmp/log3.txt");
        Controller ctr3 = new Controller(repo3);

        ProgramState program4 = ProgramState.builder().program(Examples.ex4).build();
        Repository repo4 = new MemoryRepository(program4, "/tmp/log4.txt");
        Controller ctr4 = new Controller(repo4);

        // Create the text menu
        CLI menu = new CLI();

        // Add commands to the menu
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", Examples.ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", Examples.ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", Examples.ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", Examples.ex4.toString(), ctr4));

        // Show the menu - this will run until user selects exit
        menu.show();
    }
}