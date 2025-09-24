package oxono.controller;


import javafx.application.Platform;
import oxono.model.*;
import oxono.view.MainView;
import java.util.List;


public class Controller {
    Game game;
    MainView mainview;

    public Controller(MainView mainView) {
        this.mainview = mainView;
    }

    public void actionStart(int numberOfPlayer, int sizeGrid, int AILevel) {
        game = new Game(numberOfPlayer, sizeGrid, AILevel);
        game.register(mainview);
        game.notifyObservers();
    }

    public void actionMoveTotem(List<Integer> references, String symbol) {
        if (game.getGameState() == GameState.MOVE && !game.getCurrentPlayer().isAiPlayer()) {
            Position position = new Position(references.get(0), references.get(1));
            if (symbol.equals("X")) {
                game.move(new Totem(Symbol.X), position);
            } else if (symbol.equals("O")) {
                game.move(new Totem(Symbol.O), position);
            }
            game.notifyObservers();
        }

    }

    public void actionInsert(List<Integer> references) {
        if(game.getGameState() == GameState.INSERT && !game.getCurrentPlayer().isAiPlayer() && game.insert(new Position(references.get(0), references.get(1))) ) {
            game.notifyObservers();
        }
    }

    public void AIAction() {
        game.execute();
        Platform.runLater(() -> {
            game.notifyObservers(); // Toutes les mises à jour de l'interface utilisateur ici
        });
        //game.notifyObservers();
    }

    public void undoAction() {
        game.undo();
        game.notifyObservers();
    }

    public void redoAction() {
        game.redo();
        game.notifyObservers();
    }

    public Game getGame() {
        return this.game;
    }

    public boolean isActionMoveValid(int row, int col, String symbol) {
        if (symbol.equals("X")) {
            return game.isValidMove(new Position(row, col), new Totem(Symbol.X));
        } else if (symbol.equals("O")) {
            return game.isValidMove(new Position(row, col), new Totem(Symbol.O));
        }
        return false;
    }

    public boolean isActionInsertValid(int row, int col) {
        return game.isValidInsert(row, col);
    }

    public void setRefreshBoard() {
        game.notifyObservers();
    }


}
