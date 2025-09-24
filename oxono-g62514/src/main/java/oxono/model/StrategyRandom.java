package oxono.model;

import java.util.List;

public class StrategyRandom implements Strategy{
    private Game game;
    private Board board;

    public StrategyRandom(Game game, Board board) {
        this.game = game;
        this.board = board;
    }

    private int generateRandom(int nbValue) {
        return (int) (Math.random() * nbValue);
    }

    private Totem getRandomTotem() {
        Totem totem;
        if (game.getCurrentPlayer().getCounterX() > 0 && game.getCurrentPlayer().getCounterO() > 0) {
            int randomSymbol = generateRandom(2);
            if (randomSymbol == 0) {
                totem = new Totem(Symbol.X);
            } else {
                totem = new Totem(Symbol.O);
            }

        } else if (game.getCurrentPlayer().getCounterX() == 0){
            totem = new Totem(Symbol.O);
        } else {
            totem = new Totem(Symbol.X);
        }
        return totem;
    }

    private Position getRandomPosition(Pawn pawn) {
        List<Position> positions = board.getPossible(pawn);
        return positions.get(generateRandom(positions.size()));

    }
    public void executeAlgorithm() {
        Totem randomTotem = getRandomTotem();
        Position randomTotemPosition = getRandomPosition(randomTotem);
        game.move(randomTotem, randomTotemPosition);
        Position randomTokenPosition = getRandomPosition(new Token(randomTotem.getSymbol(), game.getCurrentPlayer().getColor()));
        game.insert(randomTokenPosition);
    }

}
