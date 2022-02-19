package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StackScrollTest {

    Scroll<String> stackScroll;
    Scroll<String> testStackScroll;

    @Before
    public void setUp() {
        stackScroll = new StackScroll<>(6);
        stackScroll.insert("E");
        stackScroll.insert("D");
        stackScroll.insert("C");
        stackScroll.insert("B");
        stackScroll.insert("A");
        stackScroll.advance();
        stackScroll.advance();

        testStackScroll = new StackScroll<>(8);
        testStackScroll.insert("Z");
        testStackScroll.insert("Y");
        testStackScroll.insert("X");
        testStackScroll.insert("W");
        testStackScroll.insert("V");
    }

    @Test
    public void initSetup() {
        assertEquals(6, stackScroll.capacity());
    }

    @Test
    public void initSetupGetNext() {
        assertEquals("C", stackScroll.getNext());
    }

    @Test
    public void testInsert() {
        stackScroll.insert("Z");
        assertEquals("Z", stackScroll.getNext());
        assertEquals(4, stackScroll.rightLength());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        stackScroll.insert("X");
        stackScroll.insert(null);
        assertEquals("X", stackScroll.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateException() {
        stackScroll.insert("F");
        stackScroll.insert("G");
        stackScroll.insert("H");
        assertEquals("F", stackScroll.getNext());
    }

    @Test
    public void testSwapRightsDifferentScroll() {
        StackScroll<String> ef_gh_4 = new StackScroll<>(4);
        ef_gh_4.insert("H");
        ef_gh_4.insert("G");
        ef_gh_4.reset();
        stackScroll.swapRights(ef_gh_4);
        assertEquals("G", stackScroll.getNext());
    }

    @Test
    public void testLeftLength() {
        assertEquals(2, stackScroll.leftLength());
    }

    @Test
    public void testRightLength() {
        assertEquals(3, stackScroll.rightLength());
    }

    @Test
    public void testAdvance() {
        stackScroll.advance();
        assertEquals(3, stackScroll.leftLength());
        assertEquals(2, stackScroll.rightLength());
        assertEquals("D", stackScroll.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testAdvanceIllegalStateException() {
        stackScroll.advanceToEnd();
        stackScroll.advance();
        assertEquals("E", stackScroll.getPrevious());
    }

    @Test
    public void testRetreat() {
        stackScroll.retreat();
        assertEquals(1, stackScroll.leftLength());
        assertEquals(4, stackScroll.rightLength());
        assertEquals("B", stackScroll.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testRetreatIllegalStateException() {
        stackScroll.reset();
        stackScroll.retreat();
        assertEquals(0, stackScroll.leftLength());
    }

    @Test
    public void testReset() {
        stackScroll.reset();
        assertEquals(0, stackScroll.leftLength());
        assertEquals(5, stackScroll.rightLength());
        assertEquals("A", stackScroll.getNext());
    }

    @Test
    public void advanceToEnd() {
        stackScroll.advanceToEnd();
        assertEquals(5, stackScroll.leftLength());
        assertEquals(0, stackScroll.rightLength());
    }

    @Test
    public void testDelete() {
        String element = stackScroll.delete();
        assertEquals("C", element);
        assertEquals(2, stackScroll.rightLength());
        assertEquals("D", stackScroll.getNext());
    }


    @Test(expected = IllegalStateException.class)
    public void testDeleteException() {
        String element = stackScroll.delete();
        assertEquals("C", element);
        stackScroll.advanceToEnd();
        stackScroll.delete();
    }

    @Test
    public void testSwapRights() {
        StackScroll<String> wx_yz_6 = new StackScroll<>(6);
        wx_yz_6.insert("Z");
        wx_yz_6.insert("Y");
        wx_yz_6.insert("X");
        wx_yz_6.insert("W");
        wx_yz_6.insert("V");
        wx_yz_6.advance();
        wx_yz_6.advance();
        stackScroll.swapRights(wx_yz_6);

        assertEquals(3, stackScroll.rightLength());
        assertEquals("X", stackScroll.getNext());
        assertEquals(3, wx_yz_6.rightLength());
        assertEquals("C", wx_yz_6.getNext());
    }


    @Test(expected = IllegalStateException.class)
    public void testSwapRightsException() {
        StackScroll<String> wx_yz_6 = new StackScroll<>(6);
        wx_yz_6.insert("Z");
        wx_yz_6.insert("Y");
        wx_yz_6.insert("X");
        wx_yz_6.insert("W");
        wx_yz_6.insert("V");
        wx_yz_6.insert("U");
        wx_yz_6.advance();
        assertEquals(6, wx_yz_6.capacity());
        stackScroll.swapRights(wx_yz_6);
    }

    @Test
    public void swapRightsDifferentScroll() {
        ListScroll<String> ef_gh_4 = new ListScroll<>(3);
        ef_gh_4.insert("Y");
        ef_gh_4.insert("X");
        stackScroll.swapRights(ef_gh_4);
        assertEquals("X", stackScroll.getNext());
    }

    @Test
    public void newInstanceTest() {
        Scroll<String> temp = stackScroll.newInstance();
        assertEquals(6, temp.capacity());
        assertEquals(0, temp.leftLength());
        assertEquals(0, temp.rightLength());

    }

    @Test(expected = IllegalStateException.class)
    public void newInstanceTestException() {
        Scroll<String> temp = stackScroll.newInstance();
        assertEquals(6, temp.capacity());
        assertEquals(0, temp.leftLength());
        assertEquals(0, temp.rightLength());
        temp.retreat();
    }

}