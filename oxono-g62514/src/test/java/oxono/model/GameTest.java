package oxono.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void testMoveRight1X() {
        Game game = new Game();
        Position testPosition = new Position(3,4);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testMoveRight1O() {
        Game game = new Game();
        Position testPosition = new Position(2,3);
        game.move(new Totem(Symbol.O), testPosition);
        assertEquals(game.getPosTotemO().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemO().getRow(), testPosition.getRow());
    }

    @Test
    void testMoveDiagonal(){
        Game game = new Game();
        Position testPosition = new Position(0,0);
        assertFalse(game.move(new Totem(Symbol.X), testPosition));

    }
    @Test
    void testMoveLeft() {
        Game game = new Game();
        Position testPosition = new Position(3,2);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testMoveUp() {
        Game game = new Game();
        Position testPosition = new Position(2,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testMoveDown() {
        Game game = new Game();
        Position testPosition = new Position(4,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testMoveRow() {
        Game game = new Game();
        Position testPosition = new Position(3,0);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
        for (int i =-1; i <= 6; i++) {
            testPosition = new Position(3, i);
            if ( i == -1 || i == 0 || i == 6) {
                assertFalse(game.move(new Totem(Symbol.X), testPosition));
            } else {
                game.move(new Totem(Symbol.X), testPosition);
                assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
                assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
            }
        }
    }

    @Test
    void testMoveColumn() {
        Game game = new Game();
        Position testPosition = new Position(0,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
        for (int i =-1; i <= 6; i++) {
            testPosition = new Position(i, 3);
            if ( i == -1 || i == 0 || i == 6) {
                assertFalse(game.move(new Totem(Symbol.X), testPosition));
            } else {
                game.move(new Totem(Symbol.X), testPosition);
                assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
                assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
            }
        }
    }

    @Test
    void testRestrictionMove() {
        Board board = new Board();
        Game game = new Game(board);
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3,1));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(1,3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4,3));

        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3,5));
        assertFalse(game.move(new Totem(Symbol.X), new Position(3,0)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(3,1)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(0,3)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(1,3)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(4,3)));

        Position testPosition = new Position(3,2);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(3,4);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(3,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(2,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void test1EnclosureMove() {
        Board board = new Board();
        Game game = new Game(board);
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 2));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(2, 3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 4));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 1));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 5));

        assertFalse(game.move(new Totem(Symbol.X), new Position(3,1)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(3,5)));

        Position testPosition = new Position(3,0);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void test2EnclosureMove() {
        Board board = new Board();
        Game game = new Game(board);
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 2));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(2, 3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 4));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 3));

        assertFalse(game.move(new Totem(Symbol.X), new Position(4,3)));

        Position testPosition = new Position(5,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

    }

    @Test
    void test3EnclosureMove() {
        Board board = new Board();
        Game game = new Game(board);
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 2));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(2, 3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 4));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 3));

        assertFalse(game.move(new Totem(Symbol.X), new Position(0,3)));

        Position testPosition = new Position(1,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

    }

    @Test
    void testLeftBorderEnclosureMove() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(3,0));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(2, 0));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 0));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 1));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(5, 1));


        assertFalse(game.move(new Totem(Symbol.X), new Position(0,0)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(3,3)));

        Position testPosition = new Position(5,0);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(3,0);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(1,0);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testRightBorderEnclosureMove() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(3,5));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(2, 5));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 5));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 4));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(5, 4));


        assertFalse(game.move(new Totem(Symbol.X), new Position(0,5)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(3,2)));

        Position testPosition = new Position(5,5);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(3,5);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(1,5);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testTopBorderEnclosureMove() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(0,3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(0, 2));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(0, 4));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(1, 3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(1, 5));

        assertFalse(game.move(new Totem(Symbol.X), new Position(0,0)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(3,3)));

        Position testPosition = new Position(0,5);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(0,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(0,1);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testBottomBorderEnclosureMove() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(5,3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(5, 2));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(5, 4));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 5));

        assertFalse(game.move(new Totem(Symbol.X), new Position(5,0)));
        assertFalse(game.move(new Totem(Symbol.X), new Position(2,3)));

        Position testPosition = new Position(5,5);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(5,3);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());

        testPosition = new Position(5,1);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testFullEnclosureMove() {
        Board board = new Board();
        Game game = new Game(board);
        for (int i = 0; i < game.getBoardWidth(); i++) {
            if (i != 3) {
                board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, i));
                board.insertToken(new Token(Symbol.X, Color.PINK), new Position(i, 3));
            }
        }
        Position testPosition = new Position(2,4);
        game.move(new Totem(Symbol.X), testPosition);
        assertEquals(game.getPosTotemX().getCol(), testPosition.getCol());
        assertEquals(game.getPosTotemX().getRow(), testPosition.getRow());
    }

    @Test
    void testInsertEnclosure() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(4,3));

        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 3));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 2));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(4, 4));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(5, 3));

        Position testPosition = new Position(1,1);
        assertTrue(game.insert(testPosition));
    }

    @Test
    void testInsert() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(4,3));

        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 3));

        assertFalse(game.insert(new Position(2,3)));

        Position testPosition = new Position(5,3);
        assertTrue(game.insert(testPosition));
        testPosition = new Position(4,2);
        assertTrue(game.insert(testPosition));
        testPosition = new Position(4,4);
        assertTrue(game.insert(testPosition));
    }

    @Test
    void testWinSymbol() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(4,3));

        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 0));
        board.insertToken(new Token(Symbol.X, Color.BLACK), new Position(3, 1));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 2));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 3));

        assertTrue(game.win());
    }

    @Test
    void testWinColor() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(4,3));

        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 0));
        board.insertToken(new Token(Symbol.O, Color.PINK), new Position(3, 1));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 2));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 3));

        assertTrue(game.win());
    }
    @Test
    void testNotWinSymbol() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(4,3));

        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 0));
        board.insertToken(new Token(Symbol.X, Color.BLACK), new Position(3, 1));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 3));

        assertFalse(game.win());
    }

    @Test
    void testNotWinColor() {
        Board board = new Board();
        Game game = new Game(board);
        game.move(new Totem(Symbol.X), new Position(4,3));

        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 0));
        board.insertToken(new Token(Symbol.O, Color.PINK), new Position(3, 1));
        board.insertToken(new Token(Symbol.X, Color.PINK), new Position(3, 2));

        assertFalse(game.win());
    }

    @Test
    void testMoveWithoutToken() {
        Game game = new Game();
        for (int i = 0; i < 8; i++) {
            game.getCurrentPlayer().decreaseX();
        }
        assertFalse(game.move(new Totem(Symbol.X), new Position(4,3)));
    }

    /*@Test
    void testDraw() {
        Player playerPink = new Player(Color.PINK, false);
        Player playerBlack = new Player(Color.BLACK, false);
        Game game = new Game(playerPink, playerBlack);
        for (int i = 0; i < 8; i++) {
            playerPink.decreaseX();
            playerPink.decreaseO();
            playerBlack.decreaseX();
            playerBlack.decreaseO();
        }
        assertTrue(game.draw());
    }*/
}