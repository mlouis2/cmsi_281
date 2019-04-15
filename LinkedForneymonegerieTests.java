package linked_forneymonegerie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LinkedForneymonegerieTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Used as the basic empty menagerie to test; the @Before
    // method is run before every @Test
    LinkedForneymonegerie fm;
    @Before
    public void init () {
        fm = new LinkedForneymonegerie();
    }

    
    // =================================================
    // Unit Tests
    // =================================================
    
    @Test
    public void testSize() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
    }

    @Test
    public void testTypeSize() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(1, fm.typeSize());
        fm.collect("Burnymon");
        assertEquals(2, fm.typeSize());
    }

    @Test
    public void testCollect() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
    }

    @Test
    public void testRelease() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        assertEquals(1, fm.typeSize());
        fm.release("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
    }

    @Test
    public void testReleaseType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
        fm.releaseType("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
    }

    @Test
    public void testCountType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(2, fm.countType("Dampymon"));
        assertEquals(1, fm.countType("Burnymon"));
        assertEquals(0, fm.countType("forneymon"));
    }

    @Test
    public void testContains() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        assertFalse(fm.contains("forneymon"));
    }

    @Test
    public void testRarestType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
    }

    @Test
    public void testClone() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        LinkedForneymonegerie dolly = fm.clone();
        assertEquals(dolly.countType("Dampymon"), 2);
        assertEquals(dolly.countType("Burnymon"), 1);
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));
    }

    @Test
    public void testTrade() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Zappymon");
        fm2.collect("Leafymon");
        fm1.trade(fm2);
        assertTrue(fm1.contains("Zappymon"));
        assertTrue(fm1.contains("Leafymon"));
        assertTrue(fm2.contains("Dampymon"));
        assertTrue(fm2.contains("Burnymon"));
        assertFalse(fm1.contains("Dampymon"));
    }

    @Test
    public void testDiffMon() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Dampymon");
        fm2.collect("Zappymon");
        LinkedForneymonegerie fm3 = LinkedForneymonegerie.diffMon(fm1, fm2);
        assertEquals(fm3.countType("Dampymon"), 1);
        assertEquals(fm3.countType("Burnymon"), 1);
        assertFalse(fm3.contains("Zappymon"));
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));
    }

    @Test
    public void testSameForneymonegerie() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        assertTrue(LinkedForneymonegerie.sameCollection(fm1, fm2));
        assertTrue(LinkedForneymonegerie.sameCollection(fm2, fm1));
        fm2.collect("Leafymon");
        assertFalse(LinkedForneymonegerie.sameCollection(fm1, fm2));
    }
    
    @Test
    public void testIteratorBasics() {
        fm.collect("Andrewmon");
        fm.collect("Andrewmon");
        fm.collect("Andrewmon");
        fm.collect("Baddymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();

        // Test next()
        LinkedForneymonegerie dolly = fm.clone();
        while (true) {
            String gotten = it.getType();
            assertTrue(dolly.contains(gotten));
            dolly.release(gotten);
            if (it.hasNext()) {it.next();} else {break;}
        }
        assertTrue(dolly.empty());
        assertFalse(it.hasNext());
        
        // Test prev()
        dolly = fm.clone();
        while (true) {
            String gotten = it.getType();
            assertTrue(dolly.contains(gotten));
            dolly.release(gotten);
            if (it.hasPrev()) {it.prev();} else {break;}
        }
        // If we've seen every Forneymon that was in fm, and removed
        // that from dolly (a copy), then dolly should be empty by now
        assertTrue(dolly.empty());
        assertFalse(it.hasPrev());
        
        int countOfReplaced = fm.countType(it.getType());
//        it.replaceAll("Mimicmon");
//        assertEquals(countOfReplaced, fm.countType("Mimicmon"));
//        assertTrue(it.isValid());
//        
//        fm.collect("Cooliomon");
//        assertFalse(it.isValid());
        
        
    	//TODO: iterator should point to thing at the end
    	//if replace all adds a new type, the new type should go to the end
    	//if replace all adds a type that already exists, combine the amounts
    	//in second case, does NOT move it
    	//in case where it's pointing to what it's replacing, do nothing
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        fm2.collect("Zappymon");
        fm2.collect("Zappymon");
        fm2.collect("Zappymon");
        fm2.collect("Burnymon");
        fm2.collect("Burnymon");
        fm2.collect("Leafymon");
        fm2.collect("Leafymon");
        System.out.println(fm2);
        //Dampymon: 4, Zappymon: 3, Burnymon: 2, Leafymon: 2
        //Replace dampymon with zappy and get
        LinkedForneymonegerie.Iterator it2 = fm2.getIterator();
        it2.next();
        it2.replaceAll("Zappymon");
        System.out.println(fm2);
        //Zappymon: 7, Burnymon: 2, Leafymon: 2
        System.out.println(it2.getType());
        //Replace Zappymon with Zappymon and nothing happens
        it2.replaceAll("Zappymon");
        System.out.println(fm2);
        //Replace Zappymon with Maddiemon and get
        it2.replaceAll("Maddiemon");
        System.out.println(fm2);
        System.out.println(it2.getType());
        //Burnymon: 2, Leafymon: 2, Maddiemon: 7
    }

}
