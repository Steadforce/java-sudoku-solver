package solver;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;

public class SudokuBoardTests {
    
    SudokuIO ioHandler = new SudokuSimpleStringIO();
    
    @Test
    public void crossArraysTest() {
        SudokuBoard board = new SudokuBoard(ioHandler);
        
        
        Collection<String> crossedArrays = board.crossArrays("AB".toCharArray(), "12".toCharArray());
        
        
        assertTrue(crossedArrays.containsAll(Arrays.asList("A1","A2","B1","B2")));
    }
    
    @Test
    public void makeUnitListTest() {
        SudokuBoard board = new SudokuBoard(ioHandler);
        
        
        Collection<Collection<String>> unitList = board.makeUnitCollection();

        
        assertTrue(unitList.contains(Arrays.asList("A1","B1","C1","D1","E1","F1","G1","H1","I1")));
        assertTrue(unitList.contains(Arrays.asList("A5","B5","C5","D5","E5","F5","G5","H5","I5")));
        assertTrue(unitList.contains(Arrays.asList("A9","B9","C9","D9","E9","F9","G9","H9","I9")));
        
        assertTrue(unitList.contains(Arrays.asList("A1","A2","A3","A4","A5","A6","A7","A8","A9")));
        assertTrue(unitList.contains(Arrays.asList("D1","D2","D3","D4","D5","D6","D7","D8","D9")));
        assertTrue(unitList.contains(Arrays.asList("I1","I2","I3","I4","I5","I6","I7","I8","I9")));

        assertTrue(unitList.contains(Arrays.asList("A1","A2","A3","B1","B2","B3","C1","C2","C3")));
        assertTrue(unitList.contains(Arrays.asList("G1","G2","G3","H1","H2","H3","I1","I2","I3")));
        assertTrue(unitList.contains(Arrays.asList("A7","A8","A9","B7","B8","B9","C7","C8","C9")));
        assertTrue(unitList.contains(Arrays.asList("G7","G8","G9","H7","H8","H9","I7","I8","I9")));
    }
    
    @Test
    public void makeSquareUnitRelationsTest() {
        SudokuBoard board = new SudokuBoard(ioHandler);
        
        
        Map<String, Collection<Collection<String>>> squareUnitRelation = board.makeSquareUnitRelations(
                Arrays.asList("A1","A2","B1","B2"),
                Arrays.asList(Arrays.asList("A1","A2"), Arrays.asList("A1","B2"), Arrays.asList("A2","B2"))
                );

        
        assertTrue(squareUnitRelation.get("B1").isEmpty());
        assertTrue(squareUnitRelation.get("A1").equals(Arrays.asList(Arrays.asList("A1","A2"), Arrays.asList("A1","B2"))));
        assertTrue(squareUnitRelation.get("A2").equals(Arrays.asList(Arrays.asList("A1","A2"), Arrays.asList("A2","B2"))));
    }
    
    @Test
    public void parseBoardTest() {
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
        SudokuBoard board = new SudokuBoard(ioHandler);
        
        Map<String, Character> board1 = null;
        Map<String, Character> board2 = null;
        Map<String, Character> board3 = null;

        try {
            board1 = board.ioHandler.parseBoard(boardString1, board.getRowIndices(), board.getColumnIndices());
            board2 = board.ioHandler.parseBoard(boardString2, board.getRowIndices(), board.getColumnIndices());
            board3 = board.ioHandler.parseBoard(boardString3, board.getRowIndices(), board.getColumnIndices());
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
        SudokuBoard board = new SudokuBoard(ioHandler);
        String boardTooShort = "4.....8.5.3..........7......2.....6.....8.4......1......6.3.7.5..2.....1.4......";
        
        
        try {
            board.ioHandler.parseBoard(boardTooShort, board.getRowIndices(), board.getColumnIndices());
            fail("Exception should have been thrown but wasn't");
        } catch (BoardParsingException e) {
        }
        
        
        String boardTooLong = "4.....8.5.3..........7......2.....6.....8.4......1........6.3.7.5..2.....1.4......";
        
        
        try {
            board.ioHandler.parseBoard(boardTooLong, board.getRowIndices(), board.getColumnIndices());
            fail("Exception should have been thrown but wasn't");
        } catch (BoardParsingException e) {
        }
    }
    
    
    @Test
    public void makeSquarePeersTest() {
        Collection<String> squares = Arrays.asList("A1","A2","B1","B2");
        Map<String, Collection<Collection<String>>> squareUnitRelations = new HashMap<String, Collection<Collection<String>>>();
        squareUnitRelations.put("A1", Arrays.asList(Arrays.asList("A1","A2"),Arrays.asList("A1","B1")));
        squareUnitRelations.put("A2", Arrays.asList(Arrays.asList("A1","A2")));
        squareUnitRelations.put("B1", Arrays.asList(Arrays.asList("A1","B1")));
        squareUnitRelations.put("B2", Arrays.asList());
        SudokuBoard board = new SudokuBoard(ioHandler);
        
        
        Map<String, Collection<String>> peerMap = board.makeSquarePeers(squares, squareUnitRelations);

        
        HashSet<String> expectedA1Peers = new HashSet<String>(Arrays.asList("A2","B1"));
        HashSet<String> expectedA2Peers = new HashSet<String>(Arrays.asList("A1"));
        HashSet<String> expectedB1Peers = new HashSet<String>(Arrays.asList("A1"));
        HashSet<String> expectedB2Peers = new HashSet<String>();
        assertEquals(expectedA1Peers, peerMap.get("A1"));
        assertEquals(expectedA2Peers, peerMap.get("A2"));
        assertEquals(expectedB1Peers, peerMap.get("B1"));
        assertEquals(expectedB2Peers, peerMap.get("B2"));
    }
    
    
    @Test
    public void makeDisplayableTest_easyPuzzle() throws BoardParsingException {
        String boardInputString = "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
        SudokuBoard board = new SudokuBoard(ioHandler, boardInputString);
        SudokuSolver solver = new SudokuSolver();
        solver.processInitialBoard(board);
        String boardDisplyString =
                "4 8 3 |9 2 1 |6 5 7 \n" + 
                "9 6 7 |3 4 5 |8 2 1 \n" +
                "2 5 1 |8 7 6 |4 9 3 \n" +
                "------+------+------\n" +
                "5 4 8 |1 3 2 |9 7 6 \n" +
                "7 2 9 |5 6 4 |1 3 8 \n" +
                "1 3 6 |7 9 8 |2 4 5 \n" +
                "------+------+------\n" +
                "3 7 2 |6 8 9 |5 1 4 \n" +
                "8 1 4 |2 5 3 |7 6 9 \n" +
                "6 9 5 |4 1 7 |3 8 2 \n";
        
        
        assertEquals(boardDisplyString, board.makeDisplayable());
    }

    @Test
    public void makeDisplayableTest_hardPuzzle() throws BoardParsingException {
        String boardInputString = "4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......";
        SudokuSolver solver = new SudokuSolver();
        SudokuBoard board = new SudokuBoard(ioHandler, boardInputString);
        solver.processInitialBoard(board);
        String boardDisplyString =
                "   4      1679   12679  |  139     2369    269   |   8      1239     5    \n" + 
                " 26789     3    1256789 | 14589   24569   245689 | 12679    1249   124679 \n" + 
                "  2689   15689   125689 |   7     234569  245689 | 12369   12349   123469 \n" + 
                "------------------------+------------------------+------------------------\n" + 
                "  3789     2     15789  |  3459   34579    4579  | 13579     6     13789  \n" + 
                "  3679   15679   15679  |  359      8     25679  |   4     12359   12379  \n" + 
                " 36789     4     56789  |  359      1     25679  | 23579   23589   23789  \n" + 
                "------------------------+------------------------+------------------------\n" + 
                "  289      89     289   |   6      459      3    |  1259     7     12489  \n" + 
                "   5      6789     3    |   2      479      1    |   69     489     4689  \n" + 
                "   1      6789     4    |  589     579     5789  | 23569   23589   23689  \n";
        
        
        assertEquals(boardDisplyString, board.makeDisplayable());
    }
}
