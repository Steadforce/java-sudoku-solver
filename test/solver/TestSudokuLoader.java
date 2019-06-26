package solver;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestSudokuLoader {

    @Test
    public void getEasySudokuBoardTest() {
        SudokuLoader loader = new SudokuLoader();
        
        
        SudokuBoard board = loader.getEasySudokuBoard();
        
        
        assertNotNull(board);
    }
    
    
    @Test
    public void getHardSudokuBoardTest() {
        SudokuLoader loader = new SudokuLoader();
        
        
        SudokuBoard board = loader.getHardSudokuBoard();
        
        
        assertNotNull(board);
    }
    
    
    @Test
    public void getVeryHardSudokuBoardTest() {
        SudokuLoader loader = new SudokuLoader();
        
        
        SudokuBoard board = loader.getVeryHardSudokuBoard();
        
        
        assertNotNull(board);
    }
}
