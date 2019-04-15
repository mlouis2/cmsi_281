//Maddie Louis
package forneymonegerie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ForneymonegerieTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Used as the basic empty menagerie to test; the @Before
    // method is run before every @Test
    Forneymonegerie fm;
    @Before
    public void init () {
        fm = new Forneymonegerie();
    }

    
    // =================================================
    // Unit Tests
    // =================================================
    
    @Test
    public void testSize() {
    	assertEquals(0, fm.size());
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
        for (int i = 0; i < 20; i++) {
        	fm.collect("Zappymon");
        }
        assertEquals(23, fm.size());
        fm.release("Zappymon");
        assertEquals(22, fm.size());
        fm.release("Burnymon");
        assertEquals(21, fm.size());
    }

    @Test
    public void testTypeSize() {
    	assertEquals(0, fm.typeSize());
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(1, fm.typeSize());
        fm.collect("Burnymon");
        assertEquals(2, fm.typeSize());
        fm.collect("Zappymon");
        assertEquals(3, fm.typeSize());
        fm.releaseType("Dampymon");
        assertEquals(2, fm.typeSize());
        fm.releaseType("Zappymon");
        fm.releaseType("Burnymon");
        assertEquals(0, fm.typeSize());
    }

    @Test
    public void testCollect() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        fm.collect("Zappymon");
        assertTrue(fm.contains("Zappymon"));
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
        fm.release("Dampymon");
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        fm.collect("Burnymon");
        fm.release("Zappymon");
        assertEquals(2, fm.size());
        assertEquals(2, fm.typeSize());
        fm.release("Fakemon");
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
        fm.releaseType("Burnymon");
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());
        fm.releaseType("Fakemon");
    }

    @Test
    public void testCountType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(2, fm.countType("Dampymon"));
        assertEquals(1, fm.countType("Burnymon"));
        assertEquals(0, fm.countType("Fakemon"));
        fm.release("Dampymon");
        assertEquals(1, fm.countType("Dampymon"));
    }

    @Test
    public void testContains() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        assertFalse(fm.contains("Fakemon"));
        fm.collect("Zappymon");
        fm.release("Zappymon");
        assertFalse(fm.contains("Zappymon"));
        fm.collect("Zappymon");
        assertTrue(fm.contains("Zappymon"));
    }

    @Test
    public void testNth() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertEquals("Dampymon", fm.nth(0));
        assertEquals("Dampymon", fm.nth(1));
        assertEquals("Burnymon", fm.nth(2));
        assertEquals("Zappymon", fm.nth(3));
        fm.release("Burnymon");
        assertEquals("Zappymon", fm.nth(2));
        fm.release("Dampymon");
        fm.release("Dampymon");
        assertEquals("Zappymon", fm.nth(0));
        try {
        	fm.nth(6);
        } catch (IllegalArgumentException e) {}
        try {
        	fm.nth(-6);
        } catch (IllegalArgumentException e) {}
        fm.release("Zappymon");
        try {
        	fm.nth(0);
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testRarestType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals("Burnymon", fm.rarestType());
    }

    @Test
    public void testClone() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        Forneymonegerie dolly = fm.clone();
        assertEquals(dolly.countType("Dampymon"), 2);
        assertEquals(dolly.countType("Burnymon"), 1);
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));
        dolly.collect("Burnymon");
        assertFalse(fm.countType("Burnymon") == 2);
        Forneymonegerie fm1 = new Forneymonegerie();
        Forneymonegerie dolly1 = fm1.clone();
        assertEquals(dolly1.size(), 0);
        assertEquals(dolly1.typeSize(), 0);
    }

    @Test
    public void testTrade() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Zappymon");
        fm2.collect("Leafymon");
        fm1.trade(fm2);
        assertTrue(fm1.contains("Zappymon"));
        assertTrue(fm1.contains("Leafymon"));
        assertTrue(fm2.contains("Dampymon"));
        assertTrue(fm2.contains("Burnymon"));
        assertFalse(fm1.contains("Dampymon"));
        Forneymonegerie fm3 = new Forneymonegerie();
        fm3.collect("Burnymon");
        Forneymonegerie fm4 = new Forneymonegerie();
        fm3.trade(fm4);
        assertEquals(fm4.size(), 1);
        assertEquals(fm4.typeSize(), 1);
        assertEquals(fm3.size(), 0);
        assertEquals(fm3.typeSize(), 0);
    }

    @Test
    public void testDiffMon() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Dampymon");
        fm2.collect("Zappymon");
        Forneymonegerie fm3 = Forneymonegerie.diffMon(fm1, fm2);
        assertEquals(fm3.countType("Dampymon"), 1);
        assertEquals(fm3.countType("Burnymon"), 1);
        assertFalse(fm3.contains("Zappymon"));
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));
        Forneymonegerie fm4 = new Forneymonegerie();
        Forneymonegerie fm5 = new Forneymonegerie();
        Forneymonegerie fm6 = Forneymonegerie.diffMon(fm4, fm5);
        assertEquals(fm6.size(), 0);
        assertEquals(fm6.typeSize(), 0);
        fm4.collect("Zappymon");
        fm4.collect("Zappymon");
        fm5.collect("Burnymon");
        Forneymonegerie fm7 = Forneymonegerie.diffMon(fm4, fm5);
        assertEquals(fm7.size(), 2);
        assertEquals(fm7.typeSize(), 1);
    }

    @Test
    public void testSameForneymonegerie() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        assertTrue(Forneymonegerie.sameCollection(fm1, fm2));
        assertTrue(Forneymonegerie.sameCollection(fm2, fm1));
        fm2.collect("Leafymon");
        assertFalse(Forneymonegerie.sameCollection(fm1, fm2));
        Forneymonegerie fm3 = new Forneymonegerie();
        Forneymonegerie fm4 = new Forneymonegerie();
        assertTrue(Forneymonegerie.sameCollection(fm3, fm4));
        fm3.collect("Zappymon");
        fm3.collect("Burnymon");
        fm4.collect("Burnymon");
        fm4.collect("Zappymon");
        assertTrue(Forneymonegerie.sameCollection(fm3, fm4));
    }

}
