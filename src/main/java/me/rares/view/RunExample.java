package me.rares.view;

import me.rares.controller.Controller;

public class RunExample extends Command {
    private Controller controller;

    /**
     * Constructor for RunExample
     * @param key The menu option key
     * @param description The description (typically the program's toString())
     * @param controller The controller that will execute the program
     */
    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.execute();
            System.out.println("\nProgram executed successfully!");
        } catch (Exception e) {
            System.err.println("\nError during program execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
