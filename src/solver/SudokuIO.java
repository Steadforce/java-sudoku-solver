package solver;

import java.util.Map;

public interface SudokuIO {
    
    public Map<String, Character> parseBoard(String boardRepresentation, char[] rowIndices, char[] columnIndices) throws BoardParsingException;
    
    public String makeStringRepresentation(SudokuBoard board);

}
