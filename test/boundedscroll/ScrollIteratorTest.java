package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
public class ScrollIteratorTest {

    Scroll<String> listScroll;
    ListIterator<String> iterator;

    @Before
    public void setUp() {
        listScroll = new ListScroll<>(8);
        listScroll.insert("E");
        listScroll.insert("D");
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");
        iterator= listScroll.listIterator();
    }
    @Test
    public void testHasNext() {
        assertEquals(true,iterator.hasNext());
        listScroll.advanceToEnd();
        assertEquals(false,iterator.hasNext());
    }

    @Test
    public void testNext() {
        assertEquals("A",iterator.next());
        listScroll.advance();
        assertEquals("C",iterator.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextException() {
        assertEquals(true,iterator.hasNext());
        listScroll.advanceToEnd();
        iterator.next();

    }

    @Test
    public void testHasPrevious() {
        assertEquals(false,iterator.hasPrevious());
        listScroll.advance();
        assertEquals(true,iterator.hasPrevious());
    }

    @Test(expected = NoSuchElementException.class)
    public void testPreviousException() {
        assertEquals(false,iterator.hasPrevious());
        listScroll.reset();
        iterator.previous();

    }

    @Test
    public void testPrevious() {
        listScroll.advanceToEnd();
        assertEquals("E",iterator.previous());
    }

    @Test
    public void testNextIndex() {
        assertEquals(0, iterator.nextIndex());
        listScroll.advance();
        assertEquals(1,iterator.nextIndex());
    }

    @Test
    public void testPreviousIndex() {
        assertEquals(-1, iterator.previousIndex());
        listScroll.advance();
        assertEquals(0,iterator.previousIndex());
    }


    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void set() {
        iterator.set("X");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void add() {
        iterator.add("X");
    }

}

