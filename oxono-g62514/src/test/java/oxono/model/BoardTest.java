package oxono.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getPossibleStartTotemX() {
        Board board = new Board();
        List<Position> resultListTest = board.getPossible(new Totem(Symbol.X));
        List<Position> resultList = new ArrayList<>();
        resultList.add(new Position(0,3));
        resultList.add(new Position(1,3));
        resultList.add(new Position(2,3));
        resultList.add(new Position(3,0));
        resultList.add(new Position(3,1));
        resultList.add(new Position(3,2));
        resultList.add(new Position(3,4));
        resultList.add(new Position(3,5));
        resultList.add(new Position(4,3));
        resultList.add(new Position(5,3));
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(resultList.get(i).getRow(), resultListTest.get(i).getRow());
            assertEquals(resultList.get(i).getCol(), resultListTest.get(i).getCol());
        }
    }
    @Test
    void getPossibleStartTotemO() {
        Board board = new Board();
        List<Position> resultListTest = board.getPossible(new Totem(Symbol.O));
        List<Position> resultList = new ArrayList<>();
        resultList.add(new Position(0,2));
        resultList.add(new Position(1,2));
        resultList.add(new Position(2,0));
        resultList.add(new Position(2,1));
        resultList.add(new Position(2,3));
        resultList.add(new Position(2,4));
        resultList.add(new Position(2,5));
        resultList.add(new Position(3,2));
        resultList.add(new Position(4,2));
        resultList.add(new Position(5,2));
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(resultList.get(i).getRow(), resultListTest.get(i).getRow());
            assertEquals(resultList.get(i).getCol(), resultListTest.get(i).getCol());
        }
    }

    @Test
    void getPossibleStartTokenX() {
        Board board = new Board();
        List<Position> resultListTest = board.getPossible(new Token(Symbol.X, Color.PINK));
        List<Position> resultList = new ArrayList<>();
        resultList.add(new Position(2,3));
        resultList.add(new Position(3,2));
        resultList.add(new Position(3,4));
        resultList.add(new Position(4,3));
        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(resultList.get(i).getRow(), resultListTest.get(i).getRow());
            assertEquals(resultList.get(i).getCol(), resultListTest.get(i).getCol());
        }
    }
    @Test
    void getPossibleStarttokenO() {
        Board board = new Board();
        List<Position> resultListTest = board.getPossible(new Token(Symbol.O, Color.PINK));
        List<Position> resultList = new ArrayList<>();
        resultList.add(new Position(1,2));
        resultList.add(new Position(2,1));
        resultList.add(new Position(2,3));
        resultList.add(new Position(3,2));

        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(resultList.get(i).getRow(), resultListTest.get(i).getRow());
            assertEquals(resultList.get(i).getCol(), resultListTest.get(i).getCol());
        }
    }

}