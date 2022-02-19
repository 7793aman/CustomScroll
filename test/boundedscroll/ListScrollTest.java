package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class ListScrollTest {
    Scroll<String> listScroll;
    Scroll<String> testListScroll;

    @Before
    public void setUp() {
        listScroll = new ListScroll<>(10);
        listScroll.insert("E");
        listScroll.insert("D");
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");

        testListScroll = new ListScroll<>(8);
        testListScroll.insert("Z");
        testListScroll.insert("Y");
        testListScroll.insert("X");
        testListScroll.insert("W");
        testListScroll.insert("V");
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
        assertEquals("E", listScroll.getPrevious());
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
        assertEquals("A", listScroll.getNext());
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
        ListScroll<String> testListScroll = new ListScroll<>(6);
        testListScroll.insert("Z");
        testListScroll.insert("Y");
        testListScroll.insert("X");
        testListScroll.insert("W");
        testListScroll.insert("V");

        testListScroll.advance();
        testListScroll.advance();
        testListScroll.advance();

        listScroll.advance();
        listScroll.advance();
        listScroll.swapRights(testListScroll);

        assertEquals(2, listScroll.rightLength());
        assertEquals("Y", listScroll.getNext());
        assertEquals(3, testListScroll.rightLength());
        assertEquals("C", testListScroll.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testThisSwapRightsException() {
        ListScroll<String> testListScroll = new ListScroll<>(2);
        testListScroll.insert("Z");
        listScroll.advance();
        listScroll.swapRights(testListScroll);
        assertEquals(2, testListScroll.rightLength());
    }

    @Test(expected = IllegalStateException.class)
    public void testThisSwapRightLengthExceededException() {
        testListScroll = new ListScroll<>(10);
        testListScroll.insert("F");
        testListScroll.insert("G");
        testListScroll.insert("H");
        testListScroll.insert("I");
        testListScroll.insert("J");
        testListScroll.insert("K");
        testListScroll.insert("L");
        testListScroll.insert("M");
        testListScroll.insert("N");
        listScroll.advance();
        listScroll.advance();
        assertEquals(10, listScroll.capacity());
        listScroll.swapRights(testListScroll);

    }

    @Test(expected = IllegalStateException.class)
    public void testThatSwapRightsException() {
        ListScroll<String> listScroll = new ListScroll<>(2);
        testListScroll.insert("Z");
        listScroll.advance();
        listScroll.swapRights(testListScroll);
        assertEquals(2, testListScroll.rightLength());
    }

    @Test
    public void splice() {
        listScroll.splice(testListScroll);
        assertEquals("A", listScroll.getNext());
    }

    @Test(expected = IllegalArgumentException.class)
    public void spliceIllegalArgumentException() {
        testListScroll.advance();
        assertEquals("W", testListScroll.getNext());
        listScroll.splice(testListScroll);
    }

    @Test(expected = IllegalStateException.class)
    public void spliceCapacityExceededException() {
        testListScroll.insert("P");
        testListScroll.insert("Q");
        testListScroll.insert("R");
        listScroll.splice(testListScroll);
        assertEquals("W", testListScroll.getNext());
    }

    @Test
    public void testToString() {
        String testString = listScroll.toString();
        assertEquals("[][A, B, C, D, E]:10", testString);
        ListScroll<String> testListScroll = new ListScroll<>(6);
        testListScroll.insert("Z");
        testListScroll.insert("Y");
        testListScroll.insert("X");
        testListScroll.insert("W");
        testListScroll.insert("V");
        testListScroll.advance();
        testListScroll.advance();
        testListScroll.advance();
        String testString2 = testListScroll.toString();
        assertEquals("[V, W, X][Y, Z]:6", testString2);

        testListScroll = new ListScroll<>(1);
        String testString1 = testListScroll.toString();
        assertEquals("[][]:1", testString1);
    }

    @Test
    public void testEquals() {
        listScroll = new ListScroll<>(8);
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");

        testListScroll = new ListScroll<>(8);
        testListScroll.insert("C");
        testListScroll.insert("B");
        testListScroll.insert("A");

        boolean flag = listScroll.equals(testListScroll);
        assertEquals(true, flag);

        StackScroll<String> stackScroll = new StackScroll<>(8);
        stackScroll.insert("C");
        stackScroll.insert("B");
        stackScroll.insert("A");
        stackScroll.advance();
        listScroll.advance();

        assertEquals(true, listScroll.equals(stackScroll));

    }

    @Test
    public void testEqualsDifferent() {
        listScroll = new ListScroll<>(8);
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");

        listScroll.advance();
        testListScroll = new ListScroll<>(8);
        testListScroll.insert("Z");
        testListScroll.insert("X");
        testListScroll.insert("Y");
        assertEquals(false, listScroll.equals(testListScroll));


        testListScroll = new ListScroll<>(2);
        testListScroll.insert("C");
        assertEquals(false, listScroll.equals(testListScroll));

        testListScroll = new ListScroll<>(8);
        testListScroll.insert("C");
        testListScroll.insert("B");
        testListScroll.insert("A");

        listScroll = new ListScroll<>(8);
        listScroll.insert("D");
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");

        testListScroll.advance();
        listScroll.advance();
        assertEquals(false, listScroll.equals(testListScroll));

        testListScroll = null;
        assertEquals(false, listScroll.equals(testListScroll));

        listScroll.reset();
        testListScroll = new ListScroll<>(8);
        testListScroll.insert("Z");
        testListScroll.insert("X");
        testListScroll.insert("Y");
        testListScroll.insert("W");
        assertEquals(false, listScroll.equals(testListScroll));
    }


    @Test
    public void testHashCode() {
        listScroll = new ListScroll<>(8);
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");

        testListScroll = new StackScroll<>(8);
        testListScroll.insert("C");
        testListScroll.insert("B");
        testListScroll.insert("A");

        assertEquals(listScroll.hashCode(), testListScroll.hashCode());

        testListScroll.advance();
        assertNotEquals(listScroll.hashCode(), testListScroll.hashCode());

    }
}
