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

import org.junit.Before;
import org.junit.Test;

public class SudokuBoardTests {
    
    private SudokuIO ioHandler;
    private BoardFactory nineByNineFactory;
    
    @Before
    public void setUp() {
        this.ioHandler = new SudokuSimpleStringIO();
        this.nineByNineFactory = new NineByNineBoardFactory();
    }
    
    
    @Test
    public void makeDisplayableTest_easyPuzzle() throws BoardParsingException {
        String boardInputString = "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
        SudokuBoard board = nineByNineFactory.generateNewBoard(this.ioHandler);
        nineByNineFactory.fillBoard(board, boardInputString);
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
        SudokuBoard board = nineByNineFactory.generateNewBoard(this.ioHandler);
        nineByNineFactory.fillBoard(board, boardInputString);
        SudokuSolver solver = new SudokuSolver();
        
        
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
