package me.rares.view;

/**
 * Command to exit the application
 */
public class ExitCommand extends Command {

    /**
     * Constructor for ExitCommand
     * @param key The menu option key
     * @param description The description of the command
     */
    public ExitCommand(String key, String description) {
        super(key, description);
    }

    /**
     * Exits the application with status code 0
     */
    @Override
    public void execute() {
        System.out.println("Exiting application...");
        System.exit(0);
    }
}
