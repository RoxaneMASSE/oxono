package oxono.view;

import oxono.model.Game;
import oxono.model.Position;

public class DisplayGrid {
    public void displayGrid(Game game, int boardWidth, int boardHeight) {
        System.out.print("  ");
        for (int i=0; i < boardWidth; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < boardHeight; i++) {
            System.out.print(" " + i + " ");
            for (int j = 0; j < boardWidth; j++) {
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
                if (j < boardWidth - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }

}
