package solver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class SudokuBoard {
    private char[] allowedValues;
    private char[] rowIndices;
    private char[] columnIndices;
    private Collection<String> squareIndices;
    private Collection<Collection<String>> units;
    private Map<String, Collection<Collection<String>>> squareUnitRelations;
    private Map<String, Collection<String>> squarePeers;
    
    private Map<String, Character> initialBoard;
    private Map<String, Collection<Character>> currentBoard;
    
    public SudokuIO ioHandler;
    
    public SudokuBoard() {
        
    }

    public SudokuBoard(SudokuIO ioHandler) {
        this.ioHandler = ioHandler;
    }
    
    public SudokuBoard(SudokuIO ioHandler, String boardRepresentation) throws BoardParsingException {
        this.ioHandler = ioHandler;
        this.setInitialBoard(boardRepresentation);
    }

    public Map<String, Character> getInitialBoard() {
        return initialBoard;
    }

    public void setInitialBoard(String boardRepresentation) throws BoardParsingException {
        setInitialBoard(this.ioHandler.parseBoard(boardRepresentation, this.getRowIndices(), this.getColumnIndices()));
    }

    public void setInitialBoard(Map<String, Character> initialBoard) {
        this.initialBoard = initialBoard;
    }

    public Map<String, Collection<Character>> getCurrentBoard() {
        return currentBoard;
    }

    protected void setCurrentBoard(Map<String, Collection<Character>> currentBoard) {
        this.currentBoard = currentBoard;
    }

    public String makeDisplayable() {
        return this.ioHandler.makeStringRepresentation(this);
    }

    public char[] getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(char[] allowedValues) {
        this.allowedValues = allowedValues;
    }

    public char[] getRowIndices() {
        return rowIndices;
    }

    public void setRowIndices(char[] rowIndices) {
        this.rowIndices = rowIndices;
    }

    public char[] getColumnIndices() {
        return columnIndices;
    }

    public void setColumnIndices(char[] columnIndices) {
        this.columnIndices = columnIndices;
    }

    public Collection<String> getSquareIndices() {
        return squareIndices;
    }

    public void setSquareIndices(Collection<String> squareIndices) {
        this.squareIndices = squareIndices;
    }

    public Collection<Collection<String>> getUnits() {
        return units;
    }

    public void setUnits(Collection<Collection<String>> units) {
        this.units = units;
    }

    public Map<String, Collection<Collection<String>>> getSquareUnitRelations() {
        return squareUnitRelations;
    }

    public void setSquareUnitRelations(Map<String, Collection<Collection<String>>> squareUnitRelations) {
        this.squareUnitRelations = squareUnitRelations;
    }

    public Map<String, Collection<String>> getSquarePeers() {
        return squarePeers;
    }

    public void setSquarePeers(Map<String, Collection<String>> squarePeers) {
        this.squarePeers = squarePeers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(getColumnIndices());
        result = prime * result + Arrays.hashCode(getRowIndices());
        result = prime * result + ((getSquareIndices() == null) ? 0 : getSquareIndices().hashCode());
        result = prime * result + ((getSquareUnitRelations() == null) ? 0 : getSquareUnitRelations().hashCode());
        result = prime * result + ((getUnits() == null) ? 0 : getUnits().hashCode());
        result = prime * result + ((getInitialBoard() == null) ? 0 : getInitialBoard().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SudokuBoard other = (SudokuBoard) obj;
        if (getInitialBoard() == null) {
            if (other.getInitialBoard() != null)
                return false;
        } else if (!getInitialBoard().equals(other.getInitialBoard()))
            return false;
        if (!Arrays.equals(getColumnIndices(), other.getColumnIndices()))
            return false;
        if (!Arrays.equals(getRowIndices(), other.getRowIndices()))
            return false;
        if (getSquareIndices() == null) {
            if (other.getSquareIndices() != null)
                return false;
        } else if (!getSquareIndices().equals(other.getSquareIndices()))
            return false;
        if (getSquareUnitRelations() == null) {
            if (other.getSquareUnitRelations() != null)
                return false;
        } else if (!getSquareUnitRelations().equals(other.getSquareUnitRelations()))
            return false;
        if (getUnits() == null) {
            if (other.getUnits() != null)
                return false;
        } else if (!getUnits().equals(other.getUnits()))
            return false;
        return true;
    }
}
