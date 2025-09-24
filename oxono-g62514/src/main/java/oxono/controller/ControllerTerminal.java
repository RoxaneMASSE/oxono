package oxono.controller;

import oxono.model.*;
import oxono.view.DisplayGrid;
import oxono.view.DisplayText;
import oxono.view.MainView;

import java.util.Scanner;

public class ControllerTerminal {
    DisplayGrid displayGrid;
    Game game;
    DisplayText displayText;
    MainView mainview;
    public ControllerTerminal() {
        this.displayGrid = new DisplayGrid();
        this.displayText = new DisplayText();
    }

    public void start() {
        final Scanner clavier = new Scanner(System.in);
        displayText.displayAiPlayer();
        String inputAi = clavier.nextLine();

        game = new Game(Integer.parseInt(inputAi) , 6, 2);

        while (!game.draw() && !game.win()) {
            displayGeneralData();
            if (!game.getCurrentPlayer().isAiPlayer()) {
                if (game.getGameState() == GameState.MOVE) {
                    moveManagement(clavier);
                } else {
                    insertManagement(clavier);
                }
                displayGrid.displayGrid(game, game.getBoardWidth(), game.getBoardHeight());
                UndoRedoManagement(clavier);
            } else {
                game.execute();
            }
        }
        terminateManagement();
    }

    private void displayGeneralData() {
        displayGrid.displayGrid(game, game.getBoardWidth(), game.getBoardHeight());
        displayText.displayCounters(game.getCounters()[0], game.getCounters()[1], game.getCounters()[2], game.getCounters()[3]);
        displayText.displayFreeBoxes(game.getFreeBoxes());
        displayText.displayCurrentPlayer(game.getCurrentPlayer());
    }

    private void moveManagement(Scanner clavier) {
        Symbol symbol = null;
        String[] elements;
        do {
            displayText.displayWhichTotemMove();
            String inputSymbol = clavier.nextLine();
            displayText.displayMovePosition();
            String inputPosition = clavier.nextLine();

            if (inputSymbol.toUpperCase().equals("X")) {
                symbol = Symbol.X;
            } else if (inputSymbol.toUpperCase().equals("O")){
                symbol = Symbol.O;
            }
            elements = inputPosition.split(",");
        } while (!game.move(new Totem(symbol), new Position(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]))));
    }

    private void insertManagement(Scanner clavier) {
        String[] elements;
        do {
            displayText.displayInsertPosition();
            String inputPosition = clavier.nextLine();
            elements = inputPosition.split(",");
        } while (!game.insert(new Position(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]))));
    }

    private void UndoRedoManagement(Scanner clavier) {
        displayText.displayUndo();
        String inputUndoRedo = clavier.nextLine();
        while (inputUndoRedo.equals("undo") || inputUndoRedo.equals("redo")) {
            if (inputUndoRedo.equals("undo")) {
                game.undo();
            } else {
                game.redo();
            }
            displayGeneralData();
            displayText.displayUndoRedo();
            inputUndoRedo = clavier.nextLine();
        }
    }

    private void terminateManagement() {
        if (game.win()) {
            displayGrid.displayGrid(game, game.getBoardWidth(), game.getBoardHeight());
            displayText.displayWinner(game.getWinner().getColor().toString());
        }

        if (game.draw()) {
            displayGrid.displayGrid(game, game.getBoardWidth(), game.getBoardHeight());
            displayText.displayDraw();
        }
    }

}
