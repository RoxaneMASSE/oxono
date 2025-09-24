package oxono.model;


import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size = 6;
    private Pawn[][] grid;

    private Position posTotemX;
    private Position posTotemO;

    public Board() {
        this.grid = new Pawn[size][size];
        this.posTotemX = new Position(3, 3);
        this.posTotemO = new Position(2, 2);
        this.grid[posTotemX.getRow()][posTotemX.getCol()] = new Totem(Symbol.X);
        this.grid[posTotemO.getRow()][posTotemO.getCol()] = new Totem(Symbol.O);
    }

    Board(int size) {
        this.grid = new Pawn[size][size];
        this.posTotemX = new Position(size / 2, size / 2);
        this.posTotemO = new Position(size / 2 - 1, size / 2 - 1);

        this.grid[posTotemX.getRow()][posTotemX.getCol()] = new Totem(Symbol.X);
        this.grid[posTotemO.getRow()][posTotemO.getCol()] = new Totem(Symbol.O);
    }

    Position getPosTotemX() {
        return posTotemX;
    }

    Position getPosTotemO() {
        return posTotemO;
    }
    
    int getBoardWidth() {
        return grid[0].length;
    }

    Pawn getPawnAt(Position position) {
        return grid[position.getRow()][position.getCol()];
    }

    int getBoardHeight() {
        return grid.length;
    }

    private boolean isEmpty(Position position) {
        return this.grid[position.getRow()][position.getCol()] == null;
    }
    private boolean isEmpty(Pawn[] pawns, int i) {
        return pawns[i] == null;
    }

    private boolean isInGrid(Position position) {
        return (position.getRow() >= 0 && position.getRow() < getBoardWidth() && position.getCol() >= 0 && position.getCol() < getBoardHeight());
    }

    Pawn[] getSection(Position startPosition, Position endPosition) {
        if (startPosition.getRow() == endPosition.getRow()) {
           if (startPosition.getCol() < endPosition.getCol()) {
               return extractRight(startPosition, endPosition);
           } else {
               return extractLeft(startPosition, endPosition);
           }
        } else {
            if (startPosition.getRow() < endPosition.getRow()) {
                return extractBottom(startPosition, endPosition);
            } else {
                return extractTop(startPosition, endPosition);
            }
        }
    }

    private Pawn[] extractRight(Position startPosition, Position endPosition){
        Pawn[] pawns = new Pawn[endPosition.getCol() - startPosition.getCol() + 1];
        int counter = 0;
        for (int i = startPosition.getCol(); i <= endPosition.getCol(); i++) {
            pawns[counter] = grid[startPosition.getRow()][i];
            counter += 1;
        }
        return pawns;
    }

    private Pawn[] extractLeft(Position startPosition, Position endPosition){
        Pawn[] pawns = new Pawn[startPosition.getCol() - endPosition.getCol() + 1];
        int counter = 0;
        for (int i = startPosition.getCol(); i >= endPosition.getCol(); i--) {
            pawns[counter] = grid[startPosition.getRow()][i];
            counter += 1;
        }
        return pawns;
    }

    private Pawn[] extractTop(Position startPosition, Position endPosition){
        Pawn[] pawns = new Pawn[startPosition.getRow() - endPosition.getRow() + 1];
        int counter = 0;
        for (int i = startPosition.getRow(); i >= endPosition.getRow(); i--) {
            pawns[counter] = grid[i][startPosition.getCol()];
            counter += 1;
        }
        return pawns;
    }

    private Pawn[] extractBottom(Position startPosition, Position endPosition){
        Pawn[] pawns = new Pawn[endPosition.getRow() - startPosition.getRow() + 1];
        int counter = 0;
        for (int i = startPosition.getRow(); i <= endPosition.getRow(); i++) {
            pawns[counter] = grid[i][startPosition.getCol()];
            counter += 1;
        }
        return pawns;
    }

    private boolean isEnclosure(Position position) {
        Position[] neighbourPosition = new Position[4];
        neighbourPosition[0] = new Position(position.getRow() - 1, position.getCol());
        neighbourPosition[1] = new Position(position.getRow() + 1, position.getCol());
        neighbourPosition[2] = new Position(position.getRow(), position.getCol() - 1);
        neighbourPosition[3] = new Position(position.getRow(), position.getCol() + 1);
        int counter = 0;
        boolean isEnclosure = true;
        while(counter < neighbourPosition.length && isEnclosure ) {
            if (isInGrid(neighbourPosition[counter]) && isEmpty(neighbourPosition[counter])) {
                isEnclosure = false;
            }
            counter += 1;
        }
        return isEnclosure;
    }

    private boolean isNeighbourTotem (Position positionToken, Position positionTotem) {
        if (positionToken.getRow() == positionTotem.getRow()) {
            //On va tester en dehors de la grille mais ça n'a pas d'impact étant donné qu'on a testé que la positionToken est dans celle-ci
            return positionToken.getCol() == (positionTotem.getCol() - 1) || positionToken.getCol() == (positionTotem.getCol() + 1);
        }
        if (positionToken.getCol() == positionTotem.getCol()) {
            return positionToken.getRow() == (positionTotem.getRow() - 1) || positionToken.getRow() == (positionTotem.getRow() + 1);
        }
        return false;


    }

    private boolean isFullEnclosure(Position position) {
        Pawn[] horizontalPawns = getSection(new Position(position.getRow(), 0), new Position(position.getRow(), getBoardWidth() - 1));
        Pawn[] verticalPawns = getSection(new Position(0, position.getCol()), new Position(getBoardHeight() - 1, position.getCol()));
        int counter = 0;
        while (counter < horizontalPawns.length && ! (isEmpty(horizontalPawns, counter)) && ! (isEmpty(verticalPawns, counter))) {
            counter +=1;
        }
        return counter == horizontalPawns.length;
    }


    private Boolean isFirstFreePosition(Position startPosition, Position endPosition) {
        Pawn[] pawns = getSection(startPosition, endPosition);
        int counter = 1;
        while (counter < pawns.length && !isEmpty(pawns, counter)) {
            counter += 1;
        }
        return counter == pawns.length - 1;
    }

    private Boolean isBeforeFirstBusyPosition(Position startPosition, Position endPosition) {
        Pawn[] pawns = getSection(startPosition, endPosition);
        int counter = 1;
        while (counter < pawns.length && isEmpty(pawns, counter)) {
            counter += 1;
        }
        return counter == pawns.length;
    }

    boolean isValidMove(Totem totem, Position position) {
        Position posTotem = getPositionTotem(totem);
        if ( !isInGrid(position) || !isEmpty(position)) {
            return false;
        }
        if (!isFullEnclosure(posTotem)) {
            if (position.getRow() != posTotem.getRow() && position.getCol() != posTotem.getCol()) {
                return false;
            }
            if (isEnclosure(posTotem)) {
                return isFirstFreePosition(posTotem, position);
            } else {
                return isBeforeFirstBusyPosition(posTotem, position);
            }
        }
        return true;
    }

    void move(Totem totem, Position position) {
        if (totem.getSymbol() == Symbol.X) {
            grid[posTotemX.getRow()][posTotemX.getCol()] = null;
            posTotemX = posTotemX.changePosition(position);
            grid[posTotemX.getRow()][posTotemX.getCol()] = totem;
        } else {
            grid[posTotemO.getRow()][posTotemO.getCol()] = null;
            posTotemO = posTotemO.changePosition(position);
            grid[posTotemO.getRow()][posTotemO.getCol()] = totem;
        }
    }

    boolean isValidInsert(Token token, Position position) {
        Position posTotem;
        if (token.getSymbol() == Symbol.X) {
            posTotem = posTotemX;
        } else {
            posTotem = posTotemO;
        }
        if ( !isInGrid(position) || !isEmpty(position)) {
            return false;
        }
        if (!isEnclosure(posTotem)) {
            return isNeighbourTotem(position, posTotem);
        }
        return true;
    }
    void insertToken(Token token, Position position) {
        grid[position.getRow()][position.getCol()] = token;
    }

    void removeToken(Token token, Position position){
        grid[position.getRow()][position.getCol()] = null;
    }

    List<Position> getPossible(Pawn pawn) {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (pawn instanceof Totem) {
                    if (isValidMove((Totem) pawn, new Position(i, j))) {
                        positions.add(new Position(i, j));
                    }
                } else if (isValidInsert((Token) pawn, new Position(i, j))) {
                        positions.add(new Position(i, j));
                }
            }
        }
        return positions;
    }

    Position getPositionTotem(Totem totem) {
        if (totem.getSymbol() == Symbol.X) {
            return posTotemX;
        } else {
            return posTotemO;
        }
    }
}


