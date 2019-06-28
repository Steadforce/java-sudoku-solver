package solver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;

public class AbstractBoardFactoryTests {

    @Test
    public void crossArraysTest() {
        char[] array1 = "AB".toCharArray();
        char[] array2 = "12".toCharArray();
        
        
        Collection<String> crossedArrays = AbstractBoardFactory.crossArrays(array1, array2);
        
        
        assertTrue(crossedArrays.containsAll(Arrays.asList("A1","A2","B1","B2")));
    }
    
    @Test
    public void makeSquareUnitRelationsTest() {
        Collection<String> squares = Arrays.asList("A1","A2","B1","B2");
        Collection<Collection<String>> unitCollection =
                Arrays.asList(Arrays.asList("A1","A2"), Arrays.asList("A1","B2"), Arrays.asList("A2","B2"));
        
        
        Map<String, Collection<Collection<String>>> squareUnitRelation =
                AbstractBoardFactory.makeSquareUnitRelations(squares, unitCollection);

        
        assertTrue(squareUnitRelation.get("B1").isEmpty());
        assertTrue(squareUnitRelation.get("A1").equals(Arrays.asList(Arrays.asList("A1","A2"), Arrays.asList("A1","B2"))));
        assertTrue(squareUnitRelation.get("A2").equals(Arrays.asList(Arrays.asList("A1","A2"), Arrays.asList("A2","B2"))));
    }

    @Test
    public void testMakeSquarePeers() {
        Collection<String> squares = Arrays.asList("A1","A2","B1","B2");
        Map<String, Collection<Collection<String>>> squareUnitRelations = new HashMap<String, Collection<Collection<String>>>();
        squareUnitRelations.put("A1", Arrays.asList(Arrays.asList("A1","A2"),Arrays.asList("A1","B1")));
        squareUnitRelations.put("A2", Arrays.asList(Arrays.asList("A1","A2")));
        squareUnitRelations.put("B1", Arrays.asList(Arrays.asList("A1","B1")));
        squareUnitRelations.put("B2", Arrays.asList());
        
        
        Map<String, Collection<String>> peerMap = AbstractBoardFactory.makeSquarePeers(squares, squareUnitRelations);

        
        HashSet<String> expectedA1Peers = new HashSet<String>(Arrays.asList("A2","B1"));
        HashSet<String> expectedA2Peers = new HashSet<String>(Arrays.asList("A1"));
        HashSet<String> expectedB1Peers = new HashSet<String>(Arrays.asList("A1"));
        HashSet<String> expectedB2Peers = new HashSet<String>();
        assertEquals(expectedA1Peers, peerMap.get("A1"));
        assertEquals(expectedA2Peers, peerMap.get("A2"));
        assertEquals(expectedB1Peers, peerMap.get("B1"));
        assertEquals(expectedB2Peers, peerMap.get("B2"));
    }
}
