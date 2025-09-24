package oxono.console;

import oxono.model.*;
import oxono.view.DisplayGrid;
import oxono.view.TerminalColor;

import java.util.Scanner;

public class main {
    public static void displayGrid(Game game) {
        System.out.print("  ");
        for (int i=0; i<game.getBoardWidth(); i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < game.getBoardHeight(); i++) {
            System.out.print(" " + i + " ");
            for (int j = 0; j < game.getBoardWidth(); j++) {
                switch (game.getColorAt(new Position(i, j))) {
                    case 0:
                        System.out.print(game.getSymbolAt(new Position(i, j)));
                        break;
                    case 1:
                        System.out.print(TerminalColor.PINK + game.getSymbolAt(new Position(i, j)) + TerminalColor.DEFAULT);
                        break;
                    case 2:
                        System.out.print(TerminalColor.GREEN + game.getSymbolAt(new Position(i, j)) + TerminalColor.DEFAULT);
                        break;
                    case 3:
                        System.out.print(TerminalColor.BLUE + game.getSymbolAt(new Position(i, j)) + TerminalColor.DEFAULT);
                        break;
                }
                if (j < game.getBoardWidth() - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Symbol symbol;
        String[] elements;
        String inputPosition;
        String inputSymbol;
        Game game = new Game();
        final Scanner clavier = new Scanner(System.in);
        while (!game.draw() && !game.win()) {
            displayGrid(game);
            System.out.println();
            System.out.println("Player pink: " + game.getCounters()[0] + " X " + game.getCounters()[1] + " O");
            System.out.println("Player black: " + game.getCounters()[2] + " X " + game.getCounters()[3] + " O");
            System.out.println();
            System.out.println("Which totem do you want to move (X or O) ?");
            inputSymbol = clavier.nextLine();
            do {
                System.out.println("Where do you want to move this totem (row,column) ?");
                inputPosition = clavier.nextLine();

                if (inputSymbol.equals("X")) {
                    symbol = Symbol.X;
                } else {
                    symbol = Symbol.O;
                }
                elements = inputPosition.split(",");
            } while (!game.move(new Totem(symbol), new Position(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]))));

            displayGrid(game);

            do {
                System.out.println("Where do you want to put the token (row,column) ?");
                inputPosition = clavier.nextLine();
                elements = inputPosition.split(",");
            } while (!game.insert(new Position(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]))));
        }

        displayGrid(game);

        if (game.win()) {
            System.out.println("The winner is " + game.getWinner().getColor());
        }

        if (game.draw()) {
            System.out.println("There is no winner");
        }
    }
}
