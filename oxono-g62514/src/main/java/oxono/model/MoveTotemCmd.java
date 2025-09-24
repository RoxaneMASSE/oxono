package oxono.model;

public class MoveTotemCmd implements Command{
    private Game game;
    private Position oldPosition;
    private Position newPosition;
    private Totem totem;
    private Symbol oldToInsert;
    private Symbol newToInsert;
    private GameState oldGameState;
    private GameState newGameState;

    public MoveTotemCmd(Game game, Totem totem, Position newPosition, Symbol oldToInsert) {
        this.game = game;
        this.totem = totem;
        this.newPosition = newPosition;
        this.oldToInsert = oldToInsert;
        this.newToInsert = totem.getSymbol();
        this.oldGameState = game.getGameState();
        this.newGameState = game.getInverseGameState();
    }

    @Override
    public void execute() {
        oldPosition = game.doCommand(totem, newPosition, newToInsert, newGameState);
    }

    @Override
    public void unexecute() {
        game.undoCommand(totem, oldPosition, oldToInsert, oldGameState);
    }
}
