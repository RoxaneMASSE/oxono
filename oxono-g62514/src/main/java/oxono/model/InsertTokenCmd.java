package oxono.model;

public class InsertTokenCmd implements Command{
    private Game game;
    private Board board;
    private Token token;
    private Position newPosition;
    private int oldCounterX;
    private int oldCounterO;
    private int newCounterX;
    private int newCounterO;
    private GameState oldGameState;
    private GameState newGameState;
    private Player oldPlayer;
    private Player newPlayer;



    public InsertTokenCmd(Game game, Board board, Token token, Position newPosition, int newCounter) {
        this.game = game;
        this.board = board;
        this.token = token;
        this.newPosition = newPosition;
        this.oldGameState = game.getGameState();
        this.newGameState = game.getInverseGameState();
        this.oldPlayer = game.getCurrentPlayer();
        this.newPlayer = game.getOppositePlayer();
        this.oldCounterX = this.oldPlayer.getCounterX();
        this.oldCounterO = this.oldPlayer.getCounterO();
        if (token.getSymbol() == Symbol.X) {
            this.newCounterX = newCounter;
            this.newCounterO = this.oldCounterO;
        } else {
            this.newCounterO = newCounter;
            this.newCounterX = this.oldCounterX;
        }
    }

    @Override
    public void execute() {
        board.insertToken(token, newPosition);
        oldPlayer.setX(newCounterX);
        oldPlayer.setO(newCounterO);
        game.setGameState(newGameState);
        game.setCurrentPlayer(newPlayer);
    }

    @Override
    public void unexecute() {
        board.removeToken(token, newPosition);
        oldPlayer.setX(oldCounterX);
        oldPlayer.setO(oldCounterO);
        game.setGameState(oldGameState);
        game.setCurrentPlayer(oldPlayer);
    }
}
