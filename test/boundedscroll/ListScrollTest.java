package boundedscroll;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ListScrollTest {
    Scroll<String> listScroll;

    @Before
    public void setUp() throws Exception {
        listScroll = new ListScroll<>(6);
        listScroll.insert("E");
        listScroll.insert("D");
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");
    }

    @Test
    public void initSetup1() {
        assertEquals(6, listScroll.capacity());
    }

    @Test
    public void initSetupGetNext() {
        assertEquals("A", listScroll.getNext());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        listScroll.insert(null);
    }

    @Test
    public void testInsert() {
        listScroll.insert("Z");
        assertEquals("Z", listScroll.getNext());
        assertEquals(0, listScroll.leftLength());
        assertEquals(6, listScroll.rightLength());
    }


    @Test(expected = IllegalStateException.class)
    public void testIllegalStateException() {
        listScroll.insert("F");
        listScroll.insert("G");
    }

    @Test
    public void testLeftLength() {
        assertEquals(0, listScroll.leftLength());
        listScroll.advance();
        listScroll.advance();
        assertEquals(2, listScroll.leftLength());
    }

    @Test
    public void testRightLength() {
        assertEquals(5, listScroll.rightLength());
        listScroll.advance();
        assertEquals(4, listScroll.rightLength());
    }

    @Test
    public void testAdvance() {
        assertEquals("A", listScroll.getNext());
        assertEquals(0, listScroll.leftLength());
        assertEquals(5, listScroll.rightLength());
        listScroll.advance();
        assertEquals("B", listScroll.getNext());
        assertEquals(1, listScroll.leftLength());
        assertEquals(4, listScroll.rightLength());

    }

    @Test(expected = IllegalStateException.class)
    public void testAdvanceIllegalStateException() {
        listScroll.advanceToEnd();
        listScroll.advance();
    }

    @Test(expected = IllegalStateException.class)
    public void testRetreatIllegalStateException() {
        listScroll.retreat();
    }

    @Test
    public void testRetreat() {
        listScroll.advance();
        assertEquals("B", listScroll.getNext());
        assertEquals(1, listScroll.leftLength());
        assertEquals(4, listScroll.rightLength());
        listScroll.retreat();
        assertEquals("A", listScroll.getNext());
        assertEquals(0, listScroll.leftLength());
        assertEquals(5, listScroll.rightLength());
    }

    @Test
    public void testReset() {
        listScroll.advanceToEnd();
        assertEquals(5, listScroll.leftLength());
        assertEquals(0, listScroll.rightLength());
        listScroll.reset();
        assertEquals(0, listScroll.leftLength());
        assertEquals(5, listScroll.rightLength());
    }

    @Test
    public void advanceToEnd() {
        listScroll.advanceToEnd();
        assertEquals(5, listScroll.leftLength());
        assertEquals(0, listScroll.rightLength());
    }

    @Test
    public void testDelete() {
        String element = listScroll.delete();
        assertEquals("A", element);
        assertEquals(4, listScroll.rightLength());
        assertEquals("B", listScroll.getNext());
    }

    @Test
    public void testSwapRights() {
        ListScroll<String> wx_yz_6 = new ListScroll<>(6);
        wx_yz_6.insert("Z");
        wx_yz_6.insert("Y");
        wx_yz_6.insert("X");
        wx_yz_6.insert("W");
        wx_yz_6.insert("V");

        wx_yz_6.advance();
        wx_yz_6.advance();
        wx_yz_6.advance();

        listScroll.advance();
        listScroll.advance();

        listScroll.swapRights(wx_yz_6);

        assertEquals(2, listScroll.rightLength());
        assertEquals("Y", listScroll.getNext());
        assertEquals(3, wx_yz_6.rightLength());
        assertEquals("C", wx_yz_6.getNext());
    }


}
