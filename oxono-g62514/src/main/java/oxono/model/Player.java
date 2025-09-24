package oxono.model;

public class Player {
    private final int numberOfPawn = 8;
    private Color color;
    private int counterX;
    private int counterO;
    private boolean aiPlayer;

    public Player(Color color, Boolean aiPlayer) {
        this.color = color;
        this.counterX = numberOfPawn;
        this.counterO = numberOfPawn;
        this.aiPlayer = aiPlayer;
    }

    public Color getColor() {
        return color;
    }

    int getCounterX() {
        return counterX;
    }

    int getCounterO() {
        return counterO;
    }

    public boolean isAiPlayer() {
        return this.aiPlayer;
    }

    void decreaseX() {
        this.counterX -= 1;
    }

    void decreaseO() {
        this.counterO -= 1;
    }

    void setX(int counterX) {
        this.counterX = counterX;
    }

    void setO(int counterO) {
        this.counterO = counterO;
    }

    void setAiPlayer() {
        this.aiPlayer = true;
    }
    void setCounters(int counter) {
        this.counterX = counter;
        this.counterO = counter;
    }


}
