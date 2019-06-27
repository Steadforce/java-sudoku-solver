package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class NineByNineBoardFactory extends AbstractBoardFactory {

    @Override
    protected char[] makeAllowedValues() {
        return "123456789".toCharArray();
    }

    @Override
    protected char[] makeRowIndices() {
        return "ABCDEFGHI".toCharArray();
    }

    @Override
    protected char[] makeColumnIndices() {
        return "123456789".toCharArray();
    }

    @Override
    protected Collection<Collection<String>> makeUnitCollection() {
        Collection<Collection<String>> unitCollection = new ArrayList<Collection<String>>(27);
        addRowUnits(unitCollection);
        addColumnUnits(unitCollection);
        addSquareUnits(unitCollection);
        return unitCollection;
    }
    
    private void addRowUnits(Collection<Collection<String>> unitCollection) {
        for(char row : makeRowIndices()) {
            Collection<String> unit = new ArrayList<String>(makeColumnIndices().length);
            for(char column : makeColumnIndices()) {
                unit.add("" + row + column);
            }
            unitCollection.add(unit);
        }
    }

    private void addColumnUnits(Collection<Collection<String>> unitCollection) {
        for(char column : makeColumnIndices()) {
            Collection<String> unit = new ArrayList<String>(makeRowIndices().length);
            for(char row : makeRowIndices()) {
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

}