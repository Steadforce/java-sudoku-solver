package solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractBoardFactory implements BoardFactory {

    @Override
    public SudokuBoard generateNewBoard(SudokuIO ioHandler) {
        SudokuBoard board = new SudokuBoard(ioHandler);
        board.setAllowedValues(makeAllowedValues());
        board.setRowIndices(makeRowIndices());
        board.setColumnIndices(makeColumnIndices());
        board.setSquareIndices(crossArrays(board.getRowIndices(), board.getColumnIndices()));
        board.setUnits(makeUnitCollection());
        board.setSquareUnitRelations(makeSquareUnitRelations(board.getSquareIndices(), board.getUnits()));
        board.setSquarePeers(makeSquarePeers(board.getSquareIndices(), board.getSquareUnitRelations()));
        return board;
    }

    @Override
    public void fillBoard(SudokuBoard board, String boardRepresentation) throws BoardParsingException {
        board.setInitialBoard(boardRepresentation);
        board.setCurrentBoard(makeCurrentBoardFromInitialBoard(board.getInitialBoard()));
    }

    protected abstract char[] makeAllowedValues();
    
    protected abstract char[] makeRowIndices();

    protected abstract char[] makeColumnIndices();

    protected abstract Collection<Collection<String>> makeUnitCollection();
    
    protected static Collection<String> crossArrays(char[] array1, char[] array2){
        Collection<String> crossedCollection = new ArrayList<String>(array1.length*array2.length);
        for(char elem1 : array1) {
            for(char elem2 : array2) {
                crossedCollection.add("" + elem1 + elem2);
            }
        }
        return crossedCollection;
    }

    protected static Map<String, Collection<Collection<String>>> makeSquareUnitRelations(Collection<String> squares, Collection<Collection<String>> unitCollection) {
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
    
    protected static Map<String, Collection<String>> makeSquarePeers(Collection<String> squares, Map<String, Collection<Collection<String>>> units) {
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

    protected Map<String, Collection<Character>> makeCurrentBoardFromInitialBoard(Map<String, Character> initialBoard) {
        Map<String, Collection<Character>> currentBoard = new HashMap<String, Collection<Character>>();
        List<Character> allAllowedValues = String.valueOf(makeAllowedValues()).chars().mapToObj(e->Character.valueOf((char) e)).collect(Collectors.toList());
        initialBoard.forEach((key,value) -> {
            currentBoard.put(key, allAllowedValues.stream().collect(Collectors.toList()));
        });
        return currentBoard;
    }

}
