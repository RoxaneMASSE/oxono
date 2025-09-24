package oxono.model;

import java.util.ArrayList;
import java.util.List;

//Au lieu de faire des getters retourner un objet constant
public class Game implements Observable{
    private Board board;
    private Player playerPink;
    private Player playerBlack;
    private Symbol toInsert;
    private Player currentPlayer;
    private GameState gameState;
    private CommandManager commandManager;
    private Strategy strategy;
    private List<Observer> observers = new ArrayList<>();

    public Game() {
        this.board = new Board();
        this.playerPink = new Player(Color.PINK, false);
        this.playerBlack = new Player(Color.BLACK, false);
        this.toInsert = null;
        this.currentPlayer = playerPink;
        this.gameState = GameState.MOVE;
        this.commandManager = new CommandManager();
        this.strategy = new StrategyRandom(this, board);
    }

    public Game(int numberOfPlayer, int size, int AILevel) {
        this.board = new Board(size);
        this.playerPink = new Player(Color.PINK, false);
        this.playerBlack = new Player(Color.BLACK, false);
        this.toInsert = null;
        this.currentPlayer = playerPink;
        this.gameState = GameState.MOVE;
        this.commandManager = new CommandManager();
        if (numberOfPlayer < 2) {
            if (AILevel == 1) {
                this.strategy = new StrategyRandom(this, board);
            } else {
                this.strategy = new StrategyMiniMax(this, board);
            }
            setAiPlayer(playerPink);
        }
        setCounters(size);
    }

    Game(Board board) {
        this.board = board;
        this.playerPink = new Player(Color.PINK, false);
        this.playerBlack = new Player(Color.BLACK, false);
        this.toInsert = null;
        this.currentPlayer = playerPink;
        this.gameState = GameState.MOVE;
        this.commandManager = new CommandManager();
    }
    Game(Player playerPink, Player playerBlack) {
        this.board = new Board();
        this.playerPink = playerPink;
        this.playerBlack = playerBlack;
        this.toInsert = null;
        this.currentPlayer = playerPink;
        this.gameState = GameState.MOVE;
        this.commandManager = new CommandManager();
    }

    //Attention pas correct car si quelqu'un code et le récupère pour move comme il veut

    public Position getPosTotemX() {
        return board.getPosTotemX();
    }

    public Position getPosTotemO() {
        return board.getPosTotemO();
    }

    public int getFreeBoxes() {
        return board.getBoardHeight() * getBoardWidth() - 2 - (((getBoardHeight() * getBoardWidth()) - 4) - playerBlack.getCounterX() - playerBlack.getCounterO() - playerPink.getCounterX() - playerPink.getCounterO());
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOppositePlayer() {
        if (currentPlayer.getColor() == Color.PINK) {
            return this.playerBlack;
        } else {
            return this.playerPink;
        }
    }

    public int getBoardWidth() {
        return board.getBoardWidth();
    }

    public int getBoardHeight() {
        return board.getBoardHeight();
    }

    public String getSymbolAt (Position position) {
        if (board.getPawnAt(position) == null) {
            return " ";
        }
        return board.getPawnAt(position).getSymbol().toString();
    }

    public int getColorAt (Position position) {
        if (board.getPawnAt(position) == null) {
            return 0;
        }
        if (board.getPawnAt(position) instanceof Totem) {
            return 3;
        }
        Token token = (Token) board.getPawnAt(position);
        if (token.getColor() == Color.PINK) {
            return 1;
        } else {
            return 2;
        }
    }

    public Boolean isUndoEmpty() {
        return commandManager.isUndoEmpty();
    }

    public Boolean isRedoEmpty() {
        return commandManager.isRedoEmpty();
    }

    public int[] getCounters() {
        int[] counters = new int[4];
        counters[0] = playerPink.getCounterX();
        counters[1] = playerPink.getCounterO();
        counters[2] = playerBlack.getCounterX();
        counters[3] = playerBlack.getCounterO();
        return counters;
    }

    public GameState getGameState(){
        return gameState;
    }

    public Symbol getToInsert() {
        return toInsert;
    }

    GameState getInverseGameState() {
        if (gameState == GameState.INSERT) {
            return GameState.MOVE;
        } else {
            return GameState.INSERT;
        }
    }

    public boolean isValidInsert(int row, int column) {
        return board.isValidInsert(new Token(toInsert, currentPlayer.getColor()), new Position(row, column));
    }

    public boolean isValidMove(Position position, Totem totem) {
        return board.isValidMove(totem, position);
    }


    void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    void setCounters(int boardSize) {
        playerBlack.setCounters(((boardSize * boardSize) - 4) / 4);
        playerPink.setCounters(((boardSize * boardSize) - 4) / 4);
    }

    void setToInsert(Symbol symbol) {
        this.toInsert = symbol;
    }

    void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    void setAiPlayer(Player player) {
        player.setAiPlayer();
    }

    public boolean draw() {
        //return playerBlack.getCounterX() == 0 && playerBlack.getCounterO() == 0 && playerPink.getCounterX() == 0 && playerPink.getCounterO() == 0;
        return getFreeBoxes() == 2;
    }

    private boolean winSectionObject(Pawn[] pawns, Class classObject) {
        int i = 0;
        int nbPawnObject = 0;
        Object objectBefore = null;
        boolean win  = false;
        while (i < pawns.length && !win) {
            if (pawns[i] != null && pawns[i] instanceof Token) {
                Token currentToken = (Token) pawns[i];
                if (objectBefore != null) {
                    if (objectBefore.equals(currentToken.getSymbolOrColor(classObject))) {
                        nbPawnObject += 1;
                    } else {
                        objectBefore = currentToken.getSymbolOrColor(classObject);
                        nbPawnObject = 1;
                    }
                } else {
                    objectBefore = currentToken.getSymbolOrColor(classObject);
                    nbPawnObject = 1;
                }
            } else {
                objectBefore = null;
                nbPawnObject = 0;
            }
            if (nbPawnObject == 4) {
                win = true;
            }
            i += 1;
        }
        return win;
    }
    private boolean winSection(Pawn[] pawns) {
        if (winSectionObject(pawns, Symbol.class)) {
            return true;
        }
        return winSectionObject(pawns, Color.class);
    }

    public boolean win() {
        int i = 0;
        boolean win  = false;
        while (i < board.getBoardWidth() && !win) {
           win = winSection(board.getSection(new Position(i, 0), new Position(i, (board.getBoardWidth() - 1))));

           if (!win) {
               win = winSection(board.getSection(new Position(0, i), new Position((board.getBoardHeight() - 1), i)));
           }
           i += 1;
        }
        return win;
    }

    public Player getWinner(){
        if (currentPlayer == playerPink) {
            return playerBlack;
        }
        return playerPink;
    }

    public boolean move(Totem totem, Position position) {
        if (totem.getSymbol() == Symbol.X) {
            if (currentPlayer.getCounterX() == 0) {
                return false;
            }
        } else {
            if (currentPlayer.getCounterO() == 0) {
                return false;
            }
        }
        if (board.isValidMove(totem, position)) {
            MoveTotemCmd moveTotemCmd = new MoveTotemCmd(this, totem, position, toInsert);
            commandManager.doIt(moveTotemCmd);
            toInsert = totem.getSymbol();
            gameState = GameState.INSERT;

            return true;
        }
        return false;
    }


    public boolean insert(Position position) {
        Token token = new Token(toInsert, currentPlayer.getColor());
        int newCounter;
        if (board.isValidInsert(token, position)) {
            if (token.getSymbol() == Symbol.X) {
                newCounter = currentPlayer.getCounterX() - 1;
            } else {
                newCounter = currentPlayer.getCounterO() - 1;
            }

            InsertTokenCmd insertTokenCmd = new InsertTokenCmd(this, board, token, position, newCounter);
            commandManager.doIt(insertTokenCmd);

            return true;
        }
        return false;
    }


    public void undo(){
        if (gameState.equals(GameState.MOVE) && getOppositePlayer().isAiPlayer()) {
            this.commandManager.undo();
        }
        this.commandManager.undo();
    }

    void undoStrategy() {
        this.commandManager.undo();
    }

    public void redo() {
        if (gameState.equals(GameState.MOVE) && currentPlayer.isAiPlayer()) {
            this.commandManager.redo();
        }
        this.commandManager.redo();
    }

    public void execute() {
        strategy.executeAlgorithm();
    }

    void undoCommand(Totem totem, Position oldPosition, Symbol oldToInsert, GameState oldGameState) {
        board.move(totem, oldPosition);
        setToInsert(oldToInsert);
        setGameState(oldGameState);
    }

   Position doCommand(Totem totem, Position newPosition, Symbol newToInsert, GameState newGameState) {
        Position oldPosition = new Position(board.getPositionTotem(totem).getRow(), board.getPositionTotem(totem).getCol());
        board.move(totem, newPosition);
        setToInsert(newToInsert);
        setGameState(newGameState);
        return oldPosition;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void register(Observer o) {
        if(!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer observer :  observers) {
            observer.update();
        }
    }
}
