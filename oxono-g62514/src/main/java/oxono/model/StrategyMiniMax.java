package oxono.model;

import java.util.ArrayList;
import java.util.List;

public class StrategyMiniMax implements Strategy{
    private Game game;
    private Board board;

    public StrategyMiniMax(Game game, Board board) {
        this.game = game;
        this.board = board;
    }

    private int minimaxOnCurrentDepth(int depth, boolean isMaximizing) {
        int[] best = new int[1];
        int[] i = new int[1];
        int[] j = new int[1];
        i[0] = 0;
        j[0] = 0;
        if (depth < 4) {
            if (isMaximizing) {
                best[0] = Integer.MIN_VALUE;
            } else {
                best[0] = Integer.MAX_VALUE;
            }
            for (int idxTotem = 0; idxTotem < 2; idxTotem++) {
                List<Position> totemPositions = new ArrayList<>();
                Totem currentTotem;
                if (idxTotem == 0) {
                    currentTotem = new Totem(Symbol.X);
                } else {
                    currentTotem = new Totem(Symbol.O);
                }
                //System.out.println("(minimax-" + depth + isMaximizing + ") Totem " + idxTotem);
                totemPositions = board.getPossible(currentTotem);
                totemPositions.forEach((e) -> {
                    game.move(currentTotem, e);
                    i[0] += 1;
                    //System.out.println("(minimax-" + depth + isMaximizing + ") Totem pos. " + e.getRow() + "." + e.getCol());
                    List<Position> tokenPositions = new ArrayList<>();
                    final Token currentToken;
                    if (isMaximizing) {
                        currentToken = new Token(currentTotem.getSymbol(), Color.PINK);
                    } else {
                        currentToken = new Token(currentTotem.getSymbol(), Color.BLACK);
                    }
                    tokenPositions = board.getPossible(currentToken);
                    tokenPositions.forEach((f) -> {
                        //System.out.println("(minimax-" + depth + isMaximizing + ") Token pos. " + f.getRow() + "." + f.getCol());
                        game.insert(f);
                        j[0] += 1;
                        if (isMaximizing) {
                            best[0] = Math.max(best[0], minimax(depth + 1, false));
                        } else {
                            best[0] = Math.min(best[0], minimax(depth + 1, true));
                        }
                        /*/if (best[0] != 0) {
                            System.out.println("(minimax-" + depth + isMaximizing + " " + i[0] + "." + j[0] + ") best " + best[0]);
                        }*/
                        game.undoStrategy();
                    });
                    game.undoStrategy();
                });
            }
            return best[0];
        }
        return 0;
    }
    private int minimax(int depth, boolean isMaximizing) {
        if (game.win()) {
            //System.out.println(utility(depth, true, !isMaximizing));
            return utility(depth, true, !isMaximizing);
        }
        if (game.draw()) {
            //System.out.println(utility(depth, true, !isMaximizing));
            return utility(depth, false, !isMaximizing);
        }
        return minimaxOnCurrentDepth(depth, isMaximizing);
    }

    private int utility(int depth, Boolean isWin, boolean isMaximizing) {
        if (isWin) {
            if (isMaximizing) {
                return (100 * ((board.getBoardWidth() * board.getBoardHeight()) - 2) / 2) / depth;
            } else {
                return (-100 * ((board.getBoardWidth() * board.getBoardHeight()) - 2) / 2) / depth;
            }
        } else {
            return 0;
        }
    }

    private Integer[] findBestAction() {
        int[] best = new int[1];
        int[] idxTotem = new int[1];
        int maxTotem = 2;
        best[0] = Integer.MIN_VALUE;
        Integer[] bestAction = new Integer[5];

        if (game.getFreeBoxes() == (game.getBoardWidth()) * game.getBoardHeight() - 2) {
            maxTotem = 1;
        }

        for (idxTotem[0] = 0; idxTotem[0] < maxTotem; idxTotem[0]++) {
            List<Position> totemPositions = new ArrayList<>();
            Totem currentTotem;
            if (idxTotem[0] == 0) {
                currentTotem = new Totem(Symbol.X);
            } else {
                currentTotem = new Totem(Symbol.O);
            }
            System.out.println("(find) Totem " + idxTotem[0]);
            totemPositions = board.getPossible(currentTotem);
            totemPositions.forEach((e) -> {
                //System.out.println("(find) Totem pos. " + e.getRow() + "." + e.getCol());
                game.move(currentTotem, e);
                List<Position> tokenPositions = new ArrayList<>();
                final Token currentToken;
                currentToken = new Token(currentTotem.getSymbol(), Color.PINK);
                tokenPositions = board.getPossible(currentToken);
                tokenPositions.forEach((f) -> {
                    //System.out.println("(find) Token pos. " + f.getRow() + "." + f.getCol());
                    game.insert(f);
                    int currentBest = best[0];
                    best[0] = Math.max(best[0], minimax(1, false));
                    if (best[0] != currentBest) {
                        /* save best action ... */
                        bestAction[0] = idxTotem[0];
                        bestAction[1] = e.getRow();
                        bestAction[2] = e.getCol();
                        bestAction[3] = f.getRow();
                        bestAction[4] = f.getCol();
                    }
                    game.undoStrategy();
                });
                game.undoStrategy();
            });
        }
        return bestAction;
    }
    public void executeAlgorithm() {
        Integer[] action = findBestAction();
        Totem totem;
        if (action[0] == 0) {
            totem = new Totem(Symbol.X);
        } else {
            totem = new Totem(Symbol.O);
        }
        game.move(totem, new Position(action[1], action[2]));
        game.insert(new Position(action[3], action[4]));
    }
}

