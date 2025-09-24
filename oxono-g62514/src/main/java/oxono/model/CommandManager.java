package oxono.model;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;


    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public Boolean isUndoEmpty() {
        return this.undoStack.empty();
    }

    public Boolean isRedoEmpty() {
        return this.redoStack.empty();
    }
    public void doIt(Command command) {
        this.undoStack.add(command);
        command.execute();
        redoStack.clear();
    }

    public void undo() {
        if (!this.undoStack.empty()) {
            Command command = this.undoStack.pop();
            command.unexecute();
            this.redoStack.add(command);
        }

    }

    public void redo() {
        if (!this.redoStack.empty()) {
            Command command = this.redoStack.pop();
            command.execute();
            this.undoStack.add(command);
        }
    }

}
