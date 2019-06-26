package solver;

public interface SudokuProvider {

    public SudokuBoard getEasySudokuBoard();
    
    public SudokuBoard getHardSudokuBoard();
    
    public SudokuBoard getVeryHardSudokuBoard();
}
