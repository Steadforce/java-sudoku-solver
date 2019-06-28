package solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class TestSudokuSolver {
    
    SudokuIO ioHandler = new SudokuSimpleStringIO();
    BoardFactory boardFactory9x9 = new NineByNineBoardFactory();

    @Test
    public void deepcopyBoardMap() {
        Map<String, Collection<Character>> currentBoard = new HashMap<String, Collection<Character>>();
        currentBoard.put("A1", new ArrayList<>(Arrays.asList('1','2','3')));
        currentBoard.put("C3", new ArrayList<>(Arrays.asList('4','5','6')));
        SudokuSolver solver = new SudokuSolver();
        
        
        Map<String, Collection<Character>> copiedBoard = solver.deepcopyBoard(currentBoard);
        
        
        assertEquals(currentBoard, copiedBoard);
        
        
        currentBoard.get("A1").add('7');
        
        
        assertNotEquals(currentBoard, copiedBoard);
    }
    
    
    @Test
    public void solverTest_hardPuzzle() throws BoardParsingException {
        String boardInputString =
                "8 5 . |. . 2 |4 . . \n" + 
                "7 2 . |. . . |. . 9 \n" + 
                ". . 4 |. . . |. . . \n" + 
                "------+------+------\n" + 
                ". . . |1 . 7 |. . 2 \n" + 
                "3 . 5 |. . . |9 . . \n" + 
                ". 4 . |. . . |. . . \n" + 
                "------+------+------\n" + 
                ". . . |. 8 . |. 7 . \n" + 
                ". 1 7 |. . . |. . . \n" + 
                ". . . |. 3 6 |. 4 . \n";
        SudokuSolver solver = new SudokuSolver();
        SudokuBoard board = boardFactory9x9.generateNewBoard(ioHandler);
        boardFactory9x9.fillBoard(board, boardInputString);
        
        
        solver.processInitialBoard(board);
        boolean solverReturnValue = solver.solveBoard();
        
        
        String boardDisplayString =
                "8 5 9 |6 1 2 |4 3 7 \n" + 
                "7 2 3 |8 5 4 |1 6 9 \n" + 
                "1 6 4 |3 7 9 |5 2 8 \n" + 
                "------+------+------\n" + 
                "9 8 6 |1 4 7 |3 5 2 \n" + 
                "3 7 5 |2 6 8 |9 1 4 \n" + 
                "2 4 1 |5 9 3 |7 8 6 \n" + 
                "------+------+------\n" + 
                "4 3 2 |9 8 1 |6 7 5 \n" + 
                "6 1 7 |4 2 5 |8 9 3 \n" + 
                "5 9 8 |7 3 6 |2 4 1 \n";
        
        assertTrue(solverReturnValue);
        assertEquals(boardDisplayString, board.makeDisplayable());
    }
    
    
    @Test
    public void solverTest_veryHardPuzzle() throws BoardParsingException {
        String boardInputString =
                ". . . |. . 6 |. . . \r\n" + 
                ". 5 9 |. . . |. . 8 \r\n" + 
                "2 . . |. . 8 |. . . \r\n" + 
                "------+------+------\r\n" + 
                ". 4 5 |. . . |. . . \r\n" + 
                ". . 3 |. . . |. . . \r\n" + 
                ". . 6 |. . 3 |. 5 4 \r\n" + 
                "------+------+------\r\n" + 
                ". . . |3 2 5 |. . 6 \r\n" + 
                ". . . |. . . |. . . \r\n" + 
                ". . . |. . . |. . . ";
        SudokuSolver solver = new SudokuSolver();
        SudokuBoard board = boardFactory9x9.generateNewBoard(ioHandler);
        boardFactory9x9.fillBoard(board, boardInputString);
        
        
        solver.processInitialBoard(board);
        boolean solverReturnValue = solver.solveBoard();
        
        
        String boardDisplayString =
                "4 3 8 |7 9 6 |2 1 5 \n" + 
                "6 5 9 |1 3 2 |4 7 8 \n" + 
                "2 7 1 |4 5 8 |6 9 3 \n" + 
                "------+------+------\n" + 
                "8 4 5 |2 1 9 |3 6 7 \n" + 
                "7 1 3 |5 6 4 |8 2 9 \n" + 
                "9 2 6 |8 7 3 |1 5 4 \n" + 
                "------+------+------\n" + 
                "1 9 4 |3 2 5 |7 8 6 \n" + 
                "3 6 2 |9 8 7 |5 4 1 \n" + 
                "5 8 7 |6 4 1 |9 3 2 \n";
        
        assertTrue(solverReturnValue);
        assertEquals(boardDisplayString, board.makeDisplayable());
    }
    
    @Ignore
    @Test
    public void solverTest_infeasiblePuzzle() throws BoardParsingException {
        String boardInputString =
                ". . . |. . 5 |. 8 . \r\n" + 
                ". . . |6 . 1 |. 4 3 \r\n" + 
                ". . . |. . . |. . . \r\n" + 
                "------+------+------\r\n" + 
                ". 1 . |5 . . |. . . \r\n" + 
                ". . . |1 . 6 |. . . \r\n" + 
                "3 . . |. . . |. . 5 \r\n" + 
                "------+------+------\r\n" + 
                "5 3 . |. . . |. 6 1 \r\n" + 
                ". . . |. . . |. . 4 \r\n" + 
                ". . . |. . . |. . . ";
        SudokuSolver solver = new SudokuSolver();
        SudokuBoard board = boardFactory9x9.generateNewBoard(ioHandler);
        boardFactory9x9.fillBoard(board, boardInputString);
        
        solver.processInitialBoard(board);
        boolean solverReturnValue = solver.solveBoard();
        
        
        assertNull(board.getCurrentBoard());
        assertFalse(solverReturnValue);
    }

}
