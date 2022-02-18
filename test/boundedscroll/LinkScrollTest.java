package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkScrollTest {
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
        linkScroll.insert(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateException() {
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
        linkScroll.retreat();
        linkScroll.retreat();
        linkScroll.retreat();
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
        LinkedScroll<String> wx_yz_6 = new LinkedScroll<>(7);
        wx_yz_6.insert("Z");
        wx_yz_6.insert("Y");
        wx_yz_6.insert("X");
        wx_yz_6.insert("W");
        wx_yz_6.insert("V");

        wx_yz_6.advance();
        wx_yz_6.advance();


        linkScroll.advance();
        linkScroll.advance();

        linkScroll.swapRights(wx_yz_6);

        assertEquals(3, linkScroll.rightLength());
        assertEquals("X", linkScroll.getNext());
        assertEquals(3, wx_yz_6.rightLength());
        assertEquals("C", wx_yz_6.getNext());
    }



}
