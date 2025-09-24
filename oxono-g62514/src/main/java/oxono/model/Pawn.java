package oxono.model;

public abstract class Pawn {
    private Symbol symbol;

    public Pawn(Symbol symbol) {
        this.symbol = symbol;
    }

    Symbol getSymbol() {
        return symbol;
    }
}
