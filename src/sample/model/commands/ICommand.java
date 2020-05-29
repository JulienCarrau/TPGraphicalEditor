package sample.model.commands;

public interface ICommand {
    void execute();
    void undo();
    void redo();
}
