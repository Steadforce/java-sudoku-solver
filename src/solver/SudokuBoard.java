package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SudokuBoard {
    public final char[] ROW_INDICES = "ABCDEFGHI".toCharArray();
    public final char[] COLUMN_INDICES = "123456789".toCharArray();
    public final Collection<String> SQUARE_INDICES = crossArrays(ROW_INDICES, COLUMN_INDICES);
    public final Collection<Collection<String>> UNITS = makeUnitCollection();
    public final Map<String, Collection<Collection<String>>> SQUARE_UNIT_RELATIONS = makeSquareUnitRelations(SQUARE_INDICES, UNITS);
    public final Map<String, Collection<String>> SQUARE_PEERS = makeSquarePeers(SQUARE_INDICES, SQUARE_UNIT_RELATIONS);
    
    private Map<String, Collection<Character>> currentBoard;
    
    public SudokuIO ioHandler;
    private Map<String, Character> initialBoard;
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
        setInitialBoard(this.ioHandler.parseBoard(boardRepresentation, this.ROW_INDICES, this.COLUMN_INDICES));
    }

    public void setInitialBoard(Map<String, Character> initialBoard) {
        this.initialBoard = initialBoard;
        this.setCurrentBoard(initializeCurrentBoard());
    }

    private Map<String, Collection<Character>> initializeCurrentBoard() {
        Map<String, Collection<Character>> currentBoard = new HashMap<String, Collection<Character>>();
        getInitialBoard().forEach((key,value) -> currentBoard.put(key, "123456789".chars().mapToObj(e->Character.valueOf((char) e)).collect(Collectors.toList())));
        return currentBoard;
    }

    public Collection<Collection<String>> makeUnitCollection() {
        Collection<Collection<String>> unitCollection = new ArrayList<Collection<String>>(27);
        addRowUnits(unitCollection);
        addColumnUnits(unitCollection);
        addSquareUnits(unitCollection);
        return unitCollection;
    }

    public Map<String, Collection<Collection<String>>> makeSquareUnitRelations(Collection<String> squares, Collection<Collection<String>> unitCollection) {
        Map<String, Collection<Collection<String>>> squareUnitRelations = new HashMap<String, Collection<Collection<String>>>();
        for(String square : squares) {
            Collection<Collection<String>> unitsOfSquare = new ArrayList<Collection<String>>();
            for(Collection<String> unit : unitCollection) {
                if(unit.contains(square)) {
                    unitsOfSquare.add(unit);
                }
            }
            squareUnitRelations.put(square, unitsOfSquare);
        }
        return squareUnitRelations;
    }

    public Map<String, Collection<String>> makeSquarePeers(Collection<String> squares,
            Map<String, Collection<Collection<String>>> units) {
        Map<String, Collection<String>> peersPerSquare = new HashMap<String, Collection<String>>();
        
        for(String square : squares) {
            
            Set<String> peersWithoutDuplicates = new HashSet<String>();
            for(Collection<String> unit : units.get(square)) {
                peersWithoutDuplicates.addAll(unit);
            }
            peersWithoutDuplicates.remove(square);
            
            peersPerSquare.put(square, peersWithoutDuplicates);
        }
        
        return peersPerSquare;
    }

    private void addRowUnits(Collection<Collection<String>> unitCollection) {
        for(char row : ROW_INDICES) {
            Collection<String> unit = new ArrayList<String>(COLUMN_INDICES.length);
            for(char column : COLUMN_INDICES) {
                unit.add("" + row + column);
            }
            unitCollection.add(unit);
        }
    }

    private void addColumnUnits(Collection<Collection<String>> unitCollection) {
        for(char column : COLUMN_INDICES) {
            Collection<String> unit = new ArrayList<String>(ROW_INDICES.length);
            for(char row : ROW_INDICES) {
                unit.add("" + row + column);
            }
            unitCollection.add(unit);
        }
    }

    private void addSquareUnits(Collection<Collection<String>> unitCollection) {
        for(char[] squareRows : Arrays.asList("ABC".toCharArray(), "DEF".toCharArray(), "GHI".toCharArray())) {
            for(char[] squareColumns : Arrays.asList("123".toCharArray(), "456".toCharArray(), "789".toCharArray())) {
                Collection<String> unit = crossArrays(squareRows, squareColumns);
                unitCollection.add(unit);
            }
        }
    }

    public Collection<String> crossArrays(char[] array1, char[] array2) {
        Collection<String> crossedCollection = new ArrayList<String>(array1.length*array2.length);
        for(char elem1 : array1) {
            for(char elem2 : array2) {
                crossedCollection.add("" + elem1 + elem2);
            }
        }
        return crossedCollection;
    }

    public String makeDisplayable() {
        return this.ioHandler.makeStringRepresentation(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(COLUMN_INDICES);
        result = prime * result + Arrays.hashCode(ROW_INDICES);
        result = prime * result + ((SQUARE_INDICES == null) ? 0 : SQUARE_INDICES.hashCode());
        result = prime * result + ((SQUARE_UNIT_RELATIONS == null) ? 0 : SQUARE_UNIT_RELATIONS.hashCode());
        result = prime * result + ((UNITS == null) ? 0 : UNITS.hashCode());
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
        if (!Arrays.equals(COLUMN_INDICES, other.COLUMN_INDICES))
            return false;
        if (!Arrays.equals(ROW_INDICES, other.ROW_INDICES))
            return false;
        if (SQUARE_INDICES == null) {
            if (other.SQUARE_INDICES != null)
                return false;
        } else if (!SQUARE_INDICES.equals(other.SQUARE_INDICES))
            return false;
        if (SQUARE_UNIT_RELATIONS == null) {
            if (other.SQUARE_UNIT_RELATIONS != null)
                return false;
        } else if (!SQUARE_UNIT_RELATIONS.equals(other.SQUARE_UNIT_RELATIONS))
            return false;
        if (UNITS == null) {
            if (other.UNITS != null)
                return false;
        } else if (!UNITS.equals(other.UNITS))
            return false;
        return true;
    }

    public Map<String, Collection<Character>> getCurrentBoard() {
        return currentBoard;
    }

    protected void setCurrentBoard(Map<String, Collection<Character>> currentBoard) {
        this.currentBoard = currentBoard;
    }
}
