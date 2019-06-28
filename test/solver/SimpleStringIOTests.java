package solver;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SimpleStringIOTests {
    
    public char[] rowIndices;
    public char[] columnIndices;

    @Before
    public void setup() {
        this.rowIndices = "ABCDEFGHI".toCharArray();
        this.columnIndices = "123456789".toCharArray();
    }
    
    @Test
    public void parseBoardTest() {
        SudokuIO ioHandler = new SudokuSimpleStringIO();
        String boardString1 = "4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......";
        String boardString2 = "400000805\n030000000\n000700000\n020000060\n000080400\n000010000\n000603070\n500200000\n104000000";
        String boardString3 = 
                 "4 . . |. . . |8 . 5 \n"
                +". 3 . |. . . |. . . \n"
                +". . . |7 . . |. . . \n"
                +"------+------+------\n"
                +". 2 . |. . . |. 6 . \n"
                +". . . |. 8 . |4 . . \n"
                +". . . |. 1 . |. . . \n"
                +"------+------+------\n"
                +". . . |6 . 3 |. 7 . \n"
                +"5 . . |2 . . |. . . \n"
                +"1 . 4 |. . . |. . . ";
        
        Map<String, Character> board1 = null;
        Map<String, Character> board2 = null;
        Map<String, Character> board3 = null;

        try {
            board1 = ioHandler.parseBoard(boardString1, rowIndices, columnIndices);
            board2 = ioHandler.parseBoard(boardString2, rowIndices, columnIndices);
            board3 = ioHandler.parseBoard(boardString3, rowIndices, columnIndices);
        } catch (BoardParsingException e) {
            e.printStackTrace();
            fail("BoardParsingException thrown");
        }

        assertEquals(board1, board2);
        assertEquals(board1, board3);
        assertEquals(board2, board3);

        assertNotNull(board1);
        assertNotNull(board2);
        assertNotNull(board3);
    }
    
    @Test
    public void parseBoardTest_invalidBoards() {
        SudokuIO ioHandler = new SudokuSimpleStringIO();
        String boardTooShort = "4.....8.5.3..........7......2.....6.....8.4......1......6.3.7.5..2.....1.4......";
        
        
        try {
            ioHandler.parseBoard(boardTooShort, rowIndices, columnIndices);
            fail("Exception should have been thrown but wasn't");
        } catch (BoardParsingException e) {
        }
        
        
        String boardTooLong = "4.....8.5.3..........7......2.....6.....8.4......1........6.3.7.5..2.....1.4......";
        
        
        try {
            ioHandler.parseBoard(boardTooLong, rowIndices, columnIndices);
            fail("Exception should have been thrown but wasn't");
        } catch (BoardParsingException e) {
        }
    }
}
