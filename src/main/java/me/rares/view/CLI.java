package me.rares.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI {
    private final Map<String, Command> commands;

    public CLI() {
        commands = new HashMap<>();
    }

    /**
     * Add a command to the menu
     * @param command The command to add
     */
    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    /**
     * Print the menu options to console
     */
    private void printMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TOY LANGUAGE INTERPRETER - MENU");
        System.out.println("=".repeat(60));

        for (Command cmd : commands.values()) {
            String line = String.format("%4s : %s", cmd.getKey(), cmd.getDescription());
            System.out.println(line);
        }

        System.out.println("=".repeat(60));
    }

    /**
     * Show the menu and handle user input in a loop.
     * Continues until user exits.
     */
    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine().trim();

            Command cmd = commands.get(key);

            if (cmd == null) {
                System.out.println("\n*** Invalid Option ***");
                System.out.println("Please select a valid option from the menu.");
                continue;
            }

            try {
                cmd.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
