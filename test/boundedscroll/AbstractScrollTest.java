package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractScrollTest {
    Scroll<String>  listScroll;
    Scroll<String> wx_yz_6;

    @Before
    public void setUp() {
        listScroll = new ListScroll<>(8);
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
    public void capacity() {
        assertEquals(8, listScroll.capacity());
    }

    @Test
    public void listIterator() {

    }

    @Test
    public void iterator() {
    }

    @Test
    public void getNext() {
        listScroll.advance();
        assertEquals("B", listScroll.getNext());
    }

    @Test
    public void getPrevious() {
        listScroll.advance();
        assertEquals("A", listScroll.getPrevious());
    }

    @Test
    public void replace() {
        String elm= listScroll.replace("P");
        assertEquals("A", elm);
    }

    @Test
    public void reverse() {
        listScroll.reverse();
        assertEquals("E", listScroll.getNext());

    }

    @Test
    public void swapRights() {
        listScroll.advance();
        listScroll.advance();
        wx_yz_6.advance();
        wx_yz_6.advance();
        listScroll.swapRights(wx_yz_6);
        assertEquals("X", listScroll.getNext());
        assertEquals("C", wx_yz_6.getNext());
    }

    @Test
    public void splice() {
        listScroll.splice(wx_yz_6);
        assertEquals("A",listScroll.getNext());
    }

    @Test
    public void testToString() {
        String s = listScroll.toString();
        assertEquals("[A B C D E]", s);
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }
}