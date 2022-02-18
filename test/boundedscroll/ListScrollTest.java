package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class ListScrollTest {
    Scroll<String> listScroll;
    Scroll<String> wx_yz_6;

    @Before
    public void setUp() {
        listScroll = new ListScroll<>(10);
        listScroll.insert("E");
        listScroll.insert("D");
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");

        wx_yz_6 = new ListScroll<>(8);
        wx_yz_6.insert("Z");
        wx_yz_6.insert("Y");
        wx_yz_6.insert("X");
        wx_yz_6.insert("W");
        wx_yz_6.insert("V");
    }

    @Test
    public void initSetup() {
        assertEquals(0, listScroll.leftLength());
        assertEquals(5, listScroll.rightLength());
        assertEquals(10, listScroll.capacity());
    }

    @Test
    public void initSetupGetNext() {
        assertEquals("A", listScroll.getNext());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        listScroll.insert("X");
        assertEquals("X", listScroll.getNext());
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
        listScroll.insert("H");
        listScroll.insert("I");
        listScroll.insert("J");
        assertEquals("J", listScroll.getNext());
        listScroll.insert("K");
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
        assertEquals("E", listScroll.getPrevious());
        listScroll.advance();
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

    @Test(expected = IllegalStateException.class)
    public void testRetreatIllegalStateException() {
        listScroll.retreat();
        assertEquals("A", listScroll.getNext());
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

    @Test(expected = IllegalStateException.class)
    public void testDeleteException() {
        String element = listScroll.delete();
        assertEquals("A", element);
        listScroll.advanceToEnd();
        listScroll.delete();
    }

    @Test
    public void newInstanceTest() {
        Scroll<String> temp = listScroll.newInstance();
        assertEquals(10, temp.capacity());
        assertEquals(0, temp.leftLength());
        assertEquals(0, temp.rightLength());

    }

    @Test(expected = IllegalStateException.class)
    public void newInstanceTestException() {
        Scroll<String> temp = listScroll.newInstance();
        assertEquals(10, temp.capacity());
        assertEquals(0, temp.leftLength());
        assertEquals(0, temp.rightLength());
        temp.retreat();
    }

    /**
     * Abstract class test methods
     */




    @Test
    public void capacity() {
        assertEquals(10, listScroll.capacity());
    }

    @Test
    public void listIterator() {
        ListIterator<String> itr = listScroll.listIterator();
        assertEquals("A", itr.next());
        listScroll.advance();
        assertEquals("B", itr.previous());
        listScroll.reset();
        assertEquals(false, itr.hasPrevious());
        listScroll.advanceToEnd();
        assertEquals(false, itr.hasNext());
    }

    @Test
    public void iterator() {
        Iterator<String> itr = listScroll.iterator();
        assertEquals("A", itr.next());
        listScroll.advanceToEnd();
        assertEquals(false, itr.hasNext());
    }

    @Test
    public void getNext() {
        listScroll.advance();
        assertEquals("B", listScroll.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void getNextException() {
        listScroll.advanceToEnd();
        listScroll.getNext();
        assertEquals("E",listScroll.getPrevious());

    }

    @Test
    public void getPrevious() {
        listScroll.advance();
        assertEquals("A", listScroll.getPrevious());
    }

    @Test(expected = IllegalStateException.class)
    public void getPreviousException() {
        listScroll.reset();
        listScroll.getPrevious();
        assertEquals("A",listScroll.getNext());

    }

    @Test
    public void replace() {
        String elm = listScroll.replace("P");
        assertEquals("A", elm);
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceException() {
        String elm = listScroll.replace(null);
        assertEquals("A", listScroll.getNext());
    }

    @Test
    public void reverse() {
        listScroll.reverse();
        assertEquals("A", listScroll.getPrevious());
    }

    @Test(expected = IllegalStateException.class)
    public void reverseLeftLengthException() {
        listScroll.advance();
        assertEquals("B", listScroll.getNext());
        listScroll.reverse();
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

    @Test(expected = IllegalStateException.class)
    public void testThisSwapRightsException() {
        ListScroll<String> wx_yz_6 = new ListScroll<>(2);
        wx_yz_6.insert("Z");
        listScroll.advance();
        listScroll.swapRights(wx_yz_6);
        assertEquals(2, wx_yz_6.rightLength());
    }

    @Test(expected = IllegalStateException.class)
    public void testThisSwapRightLengthExceededException() {
        wx_yz_6 = new ListScroll<>(10);
        wx_yz_6.insert("F");
        wx_yz_6.insert("G");
        wx_yz_6.insert("H");
        wx_yz_6.insert("I");
        wx_yz_6.insert("J");
        wx_yz_6.insert("K");
        wx_yz_6.insert("L");
        wx_yz_6.insert("M");
        wx_yz_6.insert("N");
        listScroll.advance();
        listScroll.advance();
        assertEquals(10,listScroll.capacity());
        listScroll.swapRights(wx_yz_6);

    }

    @Test(expected = IllegalStateException.class)
    public void testThatSwapRightsException() {
        ListScroll<String> listScroll = new ListScroll<>(2);
        wx_yz_6.insert("Z");
        listScroll.advance();
        listScroll.swapRights(wx_yz_6);
        assertEquals(2, wx_yz_6.rightLength());
    }

    @Test
    public void splice(){
        listScroll.splice(wx_yz_6);
        assertEquals("A", listScroll.getNext());
    }


    @Test(expected = IllegalArgumentException.class)
    public void spliceIllegalArgumentException() {
        wx_yz_6.advance();
        assertEquals("W", wx_yz_6.getNext());
        listScroll.splice(wx_yz_6);
    }

    @Test(expected = IllegalStateException.class)
    public void spliceCapacityExceededException() {
        wx_yz_6.insert("P");
        wx_yz_6.insert("Q");
        wx_yz_6.insert("R");
        listScroll.splice(wx_yz_6);
        assertEquals("W", wx_yz_6.getNext());
    }

    @Test
    public void testToString() {
        String testString = listScroll.toString();
        assertEquals("[][A B C D E ]:10", testString);

        listScroll.advance();
        assertEquals("[A][B C D E ]:10", testString);

        ListScroll<String> scroll = new ListScroll<>(1);
        String testString1 = scroll.toString();
        assertEquals("[][]:1", testString1);
    }

    @Test
    public void testEquals() {
        listScroll = new ListScroll<>(8);
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");

        wx_yz_6 = new ListScroll<>(8);
        wx_yz_6.insert("C");
        wx_yz_6.insert("B");
        wx_yz_6.insert("A");


        boolean flag = listScroll.equals(wx_yz_6);
        assertEquals(true, flag);

        listScroll.reset();
        wx_yz_6.reset();
        listScroll.advance();
        assertEquals(false, listScroll.equals(wx_yz_6));

        listScroll.reset();
        wx_yz_6.reset();
        wx_yz_6.insert("S");
        assertEquals(false, listScroll.equals(wx_yz_6));

        listScroll = new ListScroll<>(8);
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");

        wx_yz_6 = new ListScroll<>(8);
        wx_yz_6.insert("C");
        wx_yz_6.insert("J");
        wx_yz_6.insert("A");

        assertEquals(false, listScroll.equals(wx_yz_6));

        wx_yz_6 = null;
        assertEquals(false, listScroll.equals(wx_yz_6));

        wx_yz_6 = new ListScroll<String>(2);
        wx_yz_6.insert("A");
        assertEquals(false, listScroll.equals(wx_yz_6));


    }

    @Test
    public void testHashCode() {
        listScroll = new ListScroll<>(8);
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");
        listScroll.advance();
        listScroll.advance();

        wx_yz_6 = new ListScroll<>(8);
        wx_yz_6.insert("C");
        wx_yz_6.insert("B");
        wx_yz_6.insert("A");

       assertEquals(listScroll.hashCode(), wx_yz_6.hashCode());

       wx_yz_6.insert("D");

        assertNotEquals(listScroll.hashCode(), wx_yz_6.hashCode());

    }
}
