package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkedScrollTest {
    Scroll<String> linkScroll;

    @Before
    public void setUp() {
        linkScroll = new LinkedScroll<>(6);
        linkScroll.insert("E");
        linkScroll.insert("D");
        linkScroll.insert("C");
        linkScroll.insert("B");
        linkScroll.insert("A");
    }

    @Test
    public void initSetup1() {
        assertEquals(6, linkScroll.capacity());
    }

    @Test
    public void testInsert() {
        linkScroll.insert("Z");
        assertEquals("Z", linkScroll.getNext());
        assertEquals(6, linkScroll.rightLength());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        assertEquals(5, linkScroll.rightLength());
        linkScroll.insert(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateException() {
        assertEquals(5, linkScroll.rightLength());
        linkScroll.insert("F");
        linkScroll.insert("G");
        linkScroll.insert("H");
    }

    @Test
    public void testLeftLength() {
        assertEquals(0, linkScroll.leftLength());
    }

    @org.junit.Test
    public void testRightLength() {
        assertEquals(5, linkScroll.rightLength());
    }

    @Test
    public void testAdvance() {
        linkScroll.advance();
        linkScroll.advance();
        assertEquals(3, linkScroll.rightLength());
        assertEquals("C", linkScroll.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testAdvanceIllegalStateException() {
        assertEquals(5, linkScroll.rightLength());
        linkScroll.advanceToEnd();
        linkScroll.advance();
    }

    @Test
    public void testRetreat() {
        linkScroll.advance();
        linkScroll.retreat();
        assertEquals(0, linkScroll.leftLength());
        assertEquals(5, linkScroll.rightLength());
        assertEquals("A", linkScroll.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testRetreatIllegalStateException() {
        assertEquals(5, linkScroll.rightLength());
        linkScroll.retreat();
        linkScroll.retreat();
        linkScroll.retreat();
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteIllegalStateException() {
        assertEquals(5, linkScroll.rightLength());
        linkScroll.advanceToEnd();
        linkScroll.delete();
    }

    @Test
    public void testDelete() {
        String element = linkScroll.delete();
        assertEquals("A", element);
        assertEquals(4, linkScroll.rightLength());
        assertEquals("B", linkScroll.getNext());
    }

    @Test
    public void testReset() {
        linkScroll.advance();
        linkScroll.reset();
        assertEquals(0, linkScroll.leftLength());
        assertEquals(5, linkScroll.rightLength());
        assertEquals("A", linkScroll.getNext());
    }

    @Test
    public void advanceToEnd() {
        linkScroll.advanceToEnd();
        assertEquals(5, linkScroll.leftLength());
        assertEquals(0, linkScroll.rightLength());
    }

    @Test
    public void testSwapRights() {
        LinkedScroll<String> testListScroll = new LinkedScroll<>(7);
        testListScroll.insert("Z");
        testListScroll.insert("Y");
        testListScroll.insert("X");
        testListScroll.insert("W");
        testListScroll.insert("V");

        testListScroll.advance();
        testListScroll.advance();

        linkScroll.advance();
        linkScroll.advance();
        linkScroll.swapRights(testListScroll);

        assertEquals(3, linkScroll.rightLength());
        assertEquals("X", linkScroll.getNext());
        assertEquals(3, testListScroll.rightLength());
        assertEquals("C", testListScroll.getNext());
    }

    @Test
    public void testNewInstance() {
        Scroll<String> linkScrollNew = linkScroll.newInstance();
        assertEquals(6, linkScrollNew.capacity());
        assertEquals(0, linkScrollNew.leftLength());
        assertEquals(0, linkScrollNew.rightLength());
    }

}