package solver;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class NineByNineBoardFactoryTests {
    
    @Test
    public void testGenerateNewBoard() {
        BoardFactory boardFactory = new NineByNineBoardFactory();
        SudokuIO ioHandler = new SudokuSimpleStringIO();
        
        SudokuBoard board = boardFactory.generateNewBoard(ioHandler);

        // Check indices
        assertTrue(Arrays.equals("123456789".toCharArray(), board.getColumnIndices()));
        assertTrue(Arrays.equals("ABCDEFGHI".toCharArray(), board.getRowIndices()));
        
        // Check units
        // Check columns
        assertTrue(board.getUnits().contains(Arrays.asList("A1","B1","C1","D1","E1","F1","G1","H1","I1")));
        assertTrue(board.getUnits().contains(Arrays.asList("A5","B5","C5","D5","E5","F5","G5","H5","I5")));
        assertTrue(board.getUnits().contains(Arrays.asList("A9","B9","C9","D9","E9","F9","G9","H9","I9")));
        //Check rows
        assertTrue(board.getUnits().contains(Arrays.asList("A1","A2","A3","A4","A5","A6","A7","A8","A9")));
        assertTrue(board.getUnits().contains(Arrays.asList("D1","D2","D3","D4","D5","D6","D7","D8","D9")));
        assertTrue(board.getUnits().contains(Arrays.asList("I1","I2","I3","I4","I5","I6","I7","I8","I9")));
        // Check squares
        assertTrue(board.getUnits().contains(Arrays.asList("A1","A2","A3","B1","B2","B3","C1","C2","C3")));
        assertTrue(board.getUnits().contains(Arrays.asList("G1","G2","G3","H1","H2","H3","I1","I2","I3")));
        assertTrue(board.getUnits().contains(Arrays.asList("A7","A8","A9","B7","B8","B9","C7","C8","C9")));
        assertTrue(board.getUnits().contains(Arrays.asList("G7","G8","G9","H7","H8","H9","I7","I8","I9")));
        
        // Check square unit relations
        assertEquals(3, board.getSquareUnitRelations().get("A1").size());
        assertTrue(board.getSquareUnitRelations().get("A1").contains(Arrays.asList("A1","B1","C1","D1","E1","F1","G1","H1","I1")));
        assertTrue(board.getSquareUnitRelations().get("A1").contains(Arrays.asList("A1","A2","A3","A4","A5","A6","A7","A8","A9")));
        assertTrue(board.getSquareUnitRelations().get("A1").contains(Arrays.asList("A1","A2","A3","B1","B2","B3","C1","C2","C3")));
        assertEquals(3, board.getSquareUnitRelations().get("I9").size());
        assertTrue(board.getSquareUnitRelations().get("I9").contains(Arrays.asList("A9","B9","C9","D9","E9","F9","G9","H9","I9")));
        assertTrue(board.getSquareUnitRelations().get("I9").contains(Arrays.asList("I1","I2","I3","I4","I5","I6","I7","I8","I9")));
        assertTrue(board.getSquareUnitRelations().get("I9").contains(Arrays.asList("G7","G8","G9","H7","H8","H9","I7","I8","I9")));
        
        // Check peers
        assertEquals(20, board.getSquarePeers().get("E5").size());
        assertTrue(board.getSquarePeers().get("E5").containsAll(Arrays.asList("E1","E2","E3","E4","E6","E7","E8","E9","A5","B5","C5","D5","F5","G5","H5","I5","D4","D6","F4","F6")));
    }
}
