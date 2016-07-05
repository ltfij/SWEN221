package swen221.lab6.tests;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runners.MethodSorters;

import swen221.lab6.connect.Game;
import swen221.lab6.connect.Game.Status;
import swen221.lab6.connect.core.Board;
import swen221.lab6.connect.core.Board.Token;
import swen221.lab6.connect.util.Position;

import org.junit.FixMethodOrder;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectTests {

    @Test
    public void test_CreatNewBoard() {
        String output = "|_|_|_|_|\n" + 
                        "|_|_|_|_|\n" + 
                        "|_|_|_|_|\n" + 
                        "|_|_|_|_|\n";

        Board board = new Board();
        Game game = new Game(board);
        
        assertEquals(game.getMovesPlayed(), 0);

        assertEquals(output, board.toString());
    }

    @Test
    public void test_ValidPlace() {
        String output = "|W|_|_|B|\n" + 
                        "|_|B|W|_|\n" + 
                        "|_|B|W|_|\n" + 
                        "|W|_|_|B|\n";

        Game game = new Game(new Board());
        game.placeToken(new Position(0, 0), Token.WHITE);
        game.placeToken(new Position(1, 1), Token.BLACK);
        game.placeToken(new Position(2, 2), Token.WHITE);
        game.placeToken(new Position(3, 3), Token.BLACK);
        game.placeToken(new Position(0, 3), Token.WHITE);
        game.placeToken(new Position(1, 2), Token.BLACK);
        game.placeToken(new Position(2, 1), Token.WHITE);
        game.placeToken(new Position(3, 0), Token.BLACK);
        Board board = game.getBoard();

        assertEquals(game.getMovesPlayed(), 8);

        assertEquals(output, board.toString());
    }

    @Test
    public void test_InvalidPlace_1() {
        Game game = new Game(new Board());
        try{
            game.placeToken(new Position(0, 0), Token.BLACK);
            fail("Black cannot make the first place.");
        } catch (IllegalArgumentException e) {
         // we are good
        }
    }
    
    @Test
    public void test_InvalidPlace_2() {
        Game game = new Game(new Board());
        try{
            game.placeToken(new Position(0, 0), Token.WHITE);
            game.placeToken(new Position(1, 1), Token.WHITE);
            fail("White cannot make the second place.");
        } catch (IllegalArgumentException e) {
         // we are good
        }
    }
    
    @Test
    public void test_InvalidPlace_3() {
        Game game = new Game(new Board());
        try{
            game.placeToken(new Position(0, 0), Token.WHITE);
            game.placeToken(new Position(1, 1), Token.BLACK);
            game.placeToken(new Position(2, 2), Token.WHITE);
            game.placeToken(new Position(3, 3), Token.BLACK);
            game.placeToken(new Position(0, 3), Token.WHITE);
            game.placeToken(new Position(1, 2), Token.BLACK);
            game.placeToken(new Position(2, 1), Token.WHITE);
            game.placeToken(new Position(3, 0), Token.WHITE);
            fail("Black cannot make the 8th place.");
        } catch (IllegalArgumentException e) {
         // we are good
        }
    }
    
    @Test
    public void test_InvalidPlace_4() {
        Game game = new Game(new Board());
        try{
            game.placeToken(new Position(0, 0), Token.WHITE);
            game.placeToken(new Position(1, 1), Token.BLACK);
            game.placeToken(new Position(2, 2), Token.WHITE);
            game.placeToken(new Position(3, 3), Token.BLACK);
            game.placeToken(new Position(0, 3), Token.WHITE);
            game.placeToken(new Position(1, 2), Token.BLACK);
            game.placeToken(new Position(2, 1), Token.BLACK);
            fail("Black cannot make the 7th place.");
        } catch (IllegalArgumentException e) {
         // we are good
        }
    }
    
    @Test
    public void test_InvalidPlace_5() {
        Game game = new Game(new Board());
        try{
            game.placeToken(new Position(0, 0), Token.WHITE);
            game.placeToken(new Position(1, 1), Token.BLACK);
            game.placeToken(new Position(2, 2), Token.WHITE);
            game.placeToken(new Position(1, 1), Token.BLACK);
            fail("Black cannot place on another Black.");
        } catch (IllegalArgumentException e) {
         // we are good
        }
    }
    
    @Test
    public void test_InvalidPlace_6() {
        Game game = new Game(new Board());
        try{
            game.placeToken(new Position(0, 0), Token.WHITE);
            game.placeToken(new Position(1, 1), Token.BLACK);
            game.placeToken(new Position(2, 2), Token.WHITE);
            game.placeToken(new Position(2, 2), Token.BLACK);
            fail("Black cannot place on another White.");
        } catch (IllegalArgumentException e) {
         // we are good
        }
    }
    
    @Test
    public void test_InvalidPlace_7() {
        Game game = new Game(new Board());
        try{
            game.placeToken(new Position(0, 0), Token.WHITE);
            game.placeToken(new Position(1, 1), Token.BLACK);
            game.placeToken(new Position(0, 0), Token.WHITE);
            fail("White cannot place on another White.");
        } catch (IllegalArgumentException e) {
         // we are good
        }
    }
    
    @Test
    public void test_InvalidPlace_9() {
        Game game = new Game(new Board());
        try{
            game.placeToken(new Position(0, 0), Token.WHITE);
            game.placeToken(new Position(1, 1), Token.BLACK);
            game.placeToken(new Position(1, 1), Token.WHITE);
            fail("White cannot place on another Black.");
        } catch (IllegalArgumentException e) {
         // we are good
        }
    }

    @SuppressWarnings("unused")
    @Test
    public void test_InalidPosition() {

        int[][] wrongCoords = { { -1, 0 }, { 4, 0 }, { 5, 0 }, { 0, -1 }, { 0, 4 }, { 0, 5 }, { -2, 4 }, { 5, -5 } };

        for (int[] coords : wrongCoords) {
            try {
                Position p = new Position(coords[0], coords[1]);
                fail("Illegal position accepted: (" + coords[0] + ", " + coords[1] + ")");
            } catch (IllegalArgumentException e) {
                // we are good
            }
        }
    }
    
    @Test
    public void test_ColumnWin_1() {
        Game game = new Game(new Board());
        game.placeToken(new Position(0, 0), Token.WHITE);
        game.placeToken(new Position(1, 1), Token.BLACK);
        game.placeToken(new Position(0, 1), Token.WHITE);
        game.placeToken(new Position(3, 3), Token.BLACK);
        game.placeToken(new Position(0, 2), Token.WHITE);
        game.placeToken(new Position(1, 2), Token.BLACK);
        game.placeToken(new Position(0, 3), Token.WHITE);
        
        assertTrue(game.getStatus() == Status.WHITEWON);
    }
    
    @Test
    public void test_ColumnWin_2() {
        Game game = new Game(new Board());
        game.placeToken(new Position(0, 0), Token.WHITE);
        game.placeToken(new Position(2, 0), Token.BLACK);
        game.placeToken(new Position(1, 1), Token.WHITE);
        game.placeToken(new Position(2, 1), Token.BLACK);
        game.placeToken(new Position(1, 2), Token.WHITE);
        game.placeToken(new Position(2, 2), Token.BLACK);
        game.placeToken(new Position(3, 3), Token.WHITE);
        game.placeToken(new Position(2, 3), Token.BLACK);
        
        assertTrue(game.getStatus() == Status.BLACKWON);
    }
    
    @Test
    public void test_RowWin_1() {
        Game game = new Game(new Board());
        game.placeToken(new Position(0, 1), Token.WHITE);
        game.placeToken(new Position(0, 3), Token.BLACK);
        game.placeToken(new Position(1, 1), Token.WHITE);
        game.placeToken(new Position(1, 2), Token.BLACK);
        game.placeToken(new Position(2, 1), Token.WHITE);
        game.placeToken(new Position(2, 3), Token.BLACK);
        game.placeToken(new Position(3, 1), Token.WHITE);
        
        assertTrue(game.getStatus() == Status.WHITEWON);
    }
    
    @Test
    public void test_RowWin_2() {
        Game game = new Game(new Board());
        game.placeToken(new Position(0, 0), Token.WHITE);
        game.placeToken(new Position(0, 3), Token.BLACK);
        game.placeToken(new Position(1, 1), Token.WHITE);
        game.placeToken(new Position(1, 3), Token.BLACK);
        game.placeToken(new Position(2, 0), Token.WHITE);
        game.placeToken(new Position(2, 3), Token.BLACK);
        game.placeToken(new Position(3, 1), Token.WHITE);
        game.placeToken(new Position(3, 3), Token.BLACK);
        
        assertTrue(game.getStatus() == Status.BLACKWON);
    }
    
    @Test
    public void test_StaleMate() {
        Game game = new Game(new Board());
        game.placeToken(new Position(0, 0), Token.WHITE);
        game.placeToken(new Position(3, 3), Token.BLACK);
        game.placeToken(new Position(3, 0), Token.WHITE);
        game.placeToken(new Position(0, 3), Token.BLACK);
        game.placeToken(new Position(2, 2), Token.WHITE);
        game.placeToken(new Position(1, 1), Token.BLACK);
        game.placeToken(new Position(3, 1), Token.WHITE);
        game.placeToken(new Position(0, 2), Token.BLACK);
        game.placeToken(new Position(0, 1), Token.WHITE);
        game.placeToken(new Position(3, 2), Token.BLACK);
        game.placeToken(new Position(1, 0), Token.WHITE);
        game.placeToken(new Position(1, 3), Token.BLACK);
        game.placeToken(new Position(1, 2), Token.WHITE);
        game.placeToken(new Position(1, 1), Token.BLACK);
        game.placeToken(new Position(2, 1), Token.WHITE);
        game.placeToken(new Position(2, 2), Token.BLACK);
        
        assertTrue(game.getStatus() == Status.STALEMATE);
    }
}
