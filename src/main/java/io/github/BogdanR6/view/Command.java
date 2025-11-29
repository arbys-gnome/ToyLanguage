package io.github.BogdanR6.view;

public abstract class Command {
    private final String key;
    private final String description;

    /**
     * Constructor for Command
     * @param key The menu option key (e.g., "1", "2", "0")
     * @param description The description of what the command does
     */
    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public abstract void execute() throws Exception;

    /**
     * Get the command's key
     * @return The key string
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the command's description
     * @return The description string
     */
    public String getDescription() {
        return description;
    }
}
