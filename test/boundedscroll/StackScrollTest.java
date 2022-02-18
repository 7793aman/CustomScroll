package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class StackScrollTest {

    Scroll<String> ab_cd_6;
    Scroll<String> wx_yz_6;

    @Before
    public void setUp() {
        ab_cd_6 = new StackScroll<>(6);
        ab_cd_6.insert("E");
        ab_cd_6.insert("D");
        ab_cd_6.insert("C");
        ab_cd_6.insert("B");
        ab_cd_6.insert("A");
        ab_cd_6.advance();
        ab_cd_6.advance();


        wx_yz_6 = new StackScroll<>(8);
        wx_yz_6.insert("Z");
        wx_yz_6.insert("Y");
        wx_yz_6.insert("X");
        wx_yz_6.insert("W");
        wx_yz_6.insert("V");

    }

    @Test
    public void initSetup() {
        assertEquals(6, ab_cd_6.capacity());
    }

    @Test
    public void initSetupGetNext() {
        assertEquals("C", ab_cd_6.getNext());
    }

    @Test
    public void testInsert() {
        ab_cd_6.insert("Z");
        assertEquals("Z", ab_cd_6.getNext());
        assertEquals(4, ab_cd_6.rightLength());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        ab_cd_6.insert("X");
        ab_cd_6.insert(null);
        assertEquals("X", ab_cd_6.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateException() {
        ab_cd_6.insert("F");
        ab_cd_6.insert("G");
        ab_cd_6.insert("H");
        assertEquals("F", ab_cd_6.getNext());
    }

    @Test
    public void testSwapRightsDifferentScroll() {
        StackScroll<String> ef_gh_4 = new StackScroll<>(4);
        ef_gh_4.insert("H");
        ef_gh_4.insert("G");
        ef_gh_4.reset();
        ab_cd_6.swapRights(ef_gh_4);
        assertEquals("G", ab_cd_6.getNext());
    }

    @Test
    public void testLeftLength() {
        assertEquals(2, ab_cd_6.leftLength());
    }

    @Test
    public void testRightLength() {
        assertEquals(3, ab_cd_6.rightLength());
    }

    @Test
    public void testAdvance() {
        ab_cd_6.advance();
        assertEquals(3, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());
        assertEquals("D", ab_cd_6.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testAdvanceIllegalStateException() {
        ab_cd_6.advanceToEnd();
        ab_cd_6.advance();
        assertEquals("E", ab_cd_6.getPrevious());
    }

    @Test
    public void testRetreat() {
        ab_cd_6.retreat();
        assertEquals(1, ab_cd_6.leftLength());
        assertEquals(4, ab_cd_6.rightLength());
        assertEquals("B", ab_cd_6.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testRetreatIllegalStateException() {
        ab_cd_6.reset();
        ab_cd_6.retreat();
        assertEquals(0, ab_cd_6.leftLength());
    }

    @Test
    public void testReset() {
        ab_cd_6.reset();
        assertEquals(0, ab_cd_6.leftLength());
        assertEquals(5, ab_cd_6.rightLength());
        assertEquals("A", ab_cd_6.getNext());
    }

    @Test
    public void advanceToEnd() {
        ab_cd_6.advanceToEnd();
        assertEquals(5, ab_cd_6.leftLength());
        assertEquals(0, ab_cd_6.rightLength());
    }

    @Test
    public void testDelete() {
        String element = ab_cd_6.delete();
        assertEquals("C", element);
        assertEquals(2, ab_cd_6.rightLength());
        assertEquals("D", ab_cd_6.getNext());
    }


    @Test(expected = IllegalStateException.class)
    public void testDeleteException() {
        String element = ab_cd_6.delete();
        assertEquals("C", element);
        ab_cd_6.advanceToEnd();
        ab_cd_6.delete();
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
        ab_cd_6.swapRights(wx_yz_6);

        assertEquals(3, ab_cd_6.rightLength());
        assertEquals("X", ab_cd_6.getNext());
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
        ab_cd_6.swapRights(wx_yz_6);
    }

    @Test
    public void swapRightsDifferentScroll() {
        ListScroll<String> ef_gh_4 = new ListScroll<>(3);
        ef_gh_4.insert("Y");
        ef_gh_4.insert("X");
        ab_cd_6.swapRights(ef_gh_4);
        assertEquals("X", ab_cd_6.getNext());
    }

    @Test
    public void newInstanceTest() {
        Scroll<String> temp = ab_cd_6.newInstance();
        assertEquals(6, temp.capacity());
        assertEquals(0, temp.leftLength());
        assertEquals(0, temp.rightLength());

    }

    @Test(expected = IllegalStateException.class)
    public void newInstanceTestException() {
        Scroll<String> temp = ab_cd_6.newInstance();
        assertEquals(6, temp.capacity());
        assertEquals(0, temp.leftLength());
        assertEquals(0, temp.rightLength());
        temp.retreat();
    }

}