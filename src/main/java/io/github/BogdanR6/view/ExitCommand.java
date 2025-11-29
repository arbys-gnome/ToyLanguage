package io.github.BogdanR6.view;

public class ExitCommand extends Command {
    /**
     * Constructor for ExitCommand
     * @param key The menu option key
     * @param description The description of the command
     */
    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.out.println("Exiting application...");
        System.exit(0);
    }
}
