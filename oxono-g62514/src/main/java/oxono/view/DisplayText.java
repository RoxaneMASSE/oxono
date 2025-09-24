package oxono.view;

import oxono.model.Color;
import oxono.model.Player;

public class DisplayText {

    public void displayWhichTotemMove() {
        System.out.println("Which totem do you want to move (X or O) ?");
    }

    public void displayMovePosition() {
        System.out.println("Where do you want to move this totem (row,column) ?");
    }

    public void displayInsertPosition() {
        System.out.println("Where do you want to put the token (row,column) ?");
    }

    public void displayWinner(String color) {
        System.out.println("The winner is " + color);
    }

    public void displayDraw() {
        System.out.println("There is no winner");
    }

    public void displayCounters(int counterPinkX, int counterPinkO, int counterBlackX, int counterBlackO) {
        System.out.println();
        System.out.println("Player pink: " + counterPinkX + " X " + counterPinkO + " O");
        System.out.println("Player black: " + counterBlackX + " X " + counterBlackO + " O");
        System.out.println();
    }

    public void displayFreeBoxes(int freeBoxes) {
        System.out.println("Number of boxes available : " + freeBoxes);
    }

    public void displayUndo() {
        System.out.println("Do you want to undo (undo) ? ");
    }

    public void displayUndoRedo() {
        System.out.println("Do you want to undo/redo ? ");
    }
    public void displayCurrentPlayer(Player currentPlayer) {
        System.out.println("The current Player is " + currentPlayer.getColor());
    }

    public void displayAiPlayer() {
        System.out.println("How many real player (1 or 2) ? ");
    }
}
