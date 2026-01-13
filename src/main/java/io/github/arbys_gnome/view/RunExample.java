package io.github.arbys_gnome.view;

import io.github.arbys_gnome.controller.Controller;

public class RunExample extends Command {
    private final Controller controller;

    /**
     * Constructor for RunExample
     * @param key The menu option key
     * @param description The description
     * @param controller The controller that will execute the program
     */
    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws Exception {
        controller.allStep();
    }
}
