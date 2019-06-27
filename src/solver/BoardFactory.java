package solver;

public interface BoardFactory {
    
    public SudokuBoard generateNewBoard(SudokuIO ioHandler);

    public void fillBoard(SudokuBoard board, String boardRepresentation) throws BoardParsingException;
    
}
