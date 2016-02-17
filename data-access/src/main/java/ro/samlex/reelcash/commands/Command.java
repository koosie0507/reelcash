package ro.samlex.reelcash.commands;

public interface Command {
    public boolean isExecutable();

    public void execute();
}
