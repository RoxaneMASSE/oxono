package oxono.model;

public class Token extends Pawn{
    private Color color;
    public Token(Symbol symbol, Color color) {
        super(symbol);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Object getSymbolOrColor(Class classObject) {
        if (classObject.equals(Color.class)) {
            return this.color;
        } else {
            return this.getSymbol();
        }
    }
}
