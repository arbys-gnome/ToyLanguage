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

        ProgramState program1 = new ProgramState(
                new ListExecutionStack(),
                new MapSymbolTable(),
                new MapFileTable(),
                new MapHeap(),
                new ListOutput(),
                Examples.ex1
        );

        Repository repo1 = new MemoryRepository(program1, "/tmp/log1.txt");
        Controller ctr1 = new Controller(repo1, false);


        ProgramState program2 = new ProgramState(
                new ListExecutionStack(),
                new MapSymbolTable(),
                new MapFileTable(),
                new MapHeap(),
                new ListOutput(),
                Examples.ex2
        );
        Repository repo2 = new MemoryRepository(program2, "/tmp/log2.txt");
        Controller ctr2 = new Controller(repo2, false);

        ProgramState program3 = new ProgramState(
                new ListExecutionStack(),
                new MapSymbolTable(),
                new MapFileTable(),
                new MapHeap(),
                new ListOutput(),
                Examples.ex3
        );
        Repository repo3 = new MemoryRepository(program3, "/tmp/log3.txt");
        Controller ctr3 = new Controller(repo3, false);

        ProgramState program4 = new ProgramState(
                new ListExecutionStack(),
                new MapSymbolTable(),
                new MapFileTable(),
                new MapHeap(),
                new ListOutput(),
                Examples.ex4
        );
        Repository repo4 = new MemoryRepository(program4, "/tmp/log4.txt");
        Controller ctr4 = new Controller(repo4, false);

        ProgramState program5 = new ProgramState(
                new ListExecutionStack(),
                new MapSymbolTable(),
                new MapFileTable(),
                new MapHeap(),
                new ListOutput(),
                Examples.ex5
        );
        Repository repo5 = new MemoryRepository(program5, "/tmp/log5.txt");
        Controller ctr5 = new Controller(repo5, false);

        // Create the text menu
        CLI menu = new CLI();

        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", Examples.ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", Examples.ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", Examples.ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", Examples.ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", Examples.ex5.toString(), ctr5));

        // Show the menu - this will run until user selects exit
        menu.show();
    }
}