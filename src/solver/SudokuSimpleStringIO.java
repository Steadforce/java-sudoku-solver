package solver;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class SudokuSimpleStringIO implements SudokuIO{

    @Override
    public Map<String, Character> parseBoard(String boardRepresentation, char[] rowIndices, char[] columnIndices) throws BoardParsingException {
        Map<String, Character> board = new HashMap<String, Character>();
        CharacterIterator boardRepresentationIterator = new StringCharacterIterator(boardRepresentation);
        for(char row : rowIndices) {
            for(char column : columnIndices) {
                skipFormattingCharacters(boardRepresentationIterator);
                char currentChar = boardRepresentationIterator.current();
                setBoardEntry(board, row, column, currentChar);
                boardRepresentationIterator.next();
            }
        }
        skipFormattingCharacters(boardRepresentationIterator);
        if(boardRepresentationIterator.current() != CharacterIterator.DONE) throw new BoardParsingException();
        
        return board;
    }

    private void setBoardEntry(Map<String, Character> board, char row, char column, char currentChar)
            throws BoardParsingException {
        if(".0123456789".indexOf(currentChar) >= 0) {
            if(Arrays.asList('.','0').contains((char) currentChar)) {
                board.put("" + row + column, null);
            }else {                        
                board.put("" + row + column, currentChar);
            }
        }else {
            throw new BoardParsingException();
        }
    }
    
    
    private void skipFormattingCharacters(CharacterIterator boardRepresentationIterator) {
        Set<Character> charsSet = ".0123456789".chars().mapToObj(e->(char)e).collect(Collectors.toSet());
        while(!charsSet.contains(boardRepresentationIterator.current()) && boardRepresentationIterator.current() != CharacterIterator.DONE) {
            boardRepresentationIterator.next();
        }
    }

    @Override
    public String makeStringRepresentation(SudokuBoard board) {
        int cellWidth = 1 + board.SQUARE_INDICES.stream().flatMapToInt(square -> IntStream.of(board.getCurrentBoard().get(square).size())).max().getAsInt();
        String separatingLine = makeSeparatingLine(cellWidth, board.COLUMN_INDICES.length);
        
        StringBuilder displayableGrid = new StringBuilder();
        for(char row : board.ROW_INDICES) {
            for(char column : board.COLUMN_INDICES) {
                displayableGrid.append(StringUtils.center(
                        board.getCurrentBoard().get("" + row + column).stream().map(String::valueOf).collect(Collectors.joining()),
                        cellWidth
                        ));
                if(Arrays.asList('3','6').contains(column)) displayableGrid.append('|');
            }
            displayableGrid.append("\n");
            
            if(Arrays.asList('C','F').contains(row)) {
                displayableGrid.append(separatingLine);
            }
        }
        
        return displayableGrid.toString();
    }

    private String makeSeparatingLine(int width, int numberOfColumns) {
        StringBuilder separatingLineBuilder = new StringBuilder();
        for(int i=1; i <= numberOfColumns; i++) {
            separatingLineBuilder.append(new String(new char[width]).replace("\0", "-"));
            if(i % (int) Math.sqrt(numberOfColumns) == 0 && i != numberOfColumns) {
                separatingLineBuilder.append("+");
            }
        }
        separatingLineBuilder.append("\n");
        return separatingLineBuilder.toString();
    }

}
