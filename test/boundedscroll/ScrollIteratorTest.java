package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
public class ScrollIteratorTest {

    Scroll<String> listScroll;
    ListIterator<String> itr;

    @Before
    public void setUp() {
        listScroll = new ListScroll<>(8);
        listScroll.insert("E");
        listScroll.insert("D");
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");
        itr= listScroll.listIterator();
    }
    @Test
    public void testHasNext() {
        assertEquals(true,itr.hasNext());
        listScroll.advanceToEnd();
        assertEquals(false,itr.hasNext());
    }

    @Test
    public void testNext() {
        assertEquals("A",itr.next());
        listScroll.advance();
        assertEquals("C",itr.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextException() {
        assertEquals(true,itr.hasNext());
        listScroll.advanceToEnd();
        itr.next();

    }

    @Test
    public void testHasPrevious() {
        assertEquals(false,itr.hasPrevious());
        listScroll.advance();
        assertEquals(true,itr.hasPrevious());
    }

    @Test(expected = NoSuchElementException.class)
    public void testPreviousException() {
        assertEquals(false,itr.hasPrevious());
        listScroll.reset();
        itr.previous();

    }

    @Test
    public void testPrevious() {
        listScroll.advanceToEnd();
        assertEquals("E",itr.previous());
    }

    @Test
    public void testNextIndex() {
        assertEquals(0, itr.nextIndex());
        listScroll.advance();
        assertEquals(1,itr.nextIndex());
    }

    @Test
    public void testPreviousIndex() {
        assertEquals(-1, itr.previousIndex());
        listScroll.advance();
        assertEquals(0,itr.previousIndex());
    }


    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        itr.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void set() {
        itr.set("X");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void add() {
        itr.add("X");
    }

}

