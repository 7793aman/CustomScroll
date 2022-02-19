package boundedscroll;

import java.util.ListIterator;

/**
 * A cursor based scroll data structure. Elements in the structure may not be null
 *
 * @param <E> the type of  elements in the structure
 * @author Aman Jain
 */
public interface Scroll<E> extends Iterable<E> {

    /**
     * Adds the specified element to the right of the cursor in this scroll.
     *
     * @param element
     * @throws IllegalArgumentException if the element is null
     * @throws IllegalStateException    if the scroll is full
     */
    public void insert(E element) throws IllegalArgumentException, IllegalStateException;


    /**
     * Deletes the element to the right of the cursor
     *
     * @return E the element that was deleted
     * @throws IllegalStateException if the scroll is empty
     */
    public E delete() throws IllegalStateException;


    /**
     * Advance the cursor, one element to rhe right
     *
     * @throws IllegalStateException if the cursor is at the end of the scroll
     */
    public void advance() throws IllegalStateException;


    /**
     * Moves the cursor one element to the left
     *
     * @throws IllegalStateException if the cursor is at the beginning of the scroll
     */
    public void retreat() throws IllegalStateException;


    /**
     * Resets the cursor to the beginning of the scroll
     */
    public void reset();


    /**
     * Advances the cursor to the end of the scroll
     */
    public void advanceToEnd();


    /**
     * Swaps the right part of this scroll with the right part of that scroll
     *
     * @param that
     * @throws IllegalStateException if the size of this or that exceeds the respective capacity while swapping
     */
    public void swapRights(Scroll<E> that) throws IllegalStateException;


    /**
     * Returns the number of elements to the left of the cursor
     *
     * @return the number of elements to the left of the cursor
     */
    public int leftLength();


    /**
     * Returns the number of elements to the right of the cursor
     *
     * @return the number of elements to the left of the cursor
     */
    public int rightLength();


    /**
     * Returns the maximum number of elements the scroll structure can contain.
     *
     * @return the maximum number of elements the scroll structure can contain.
     */
    public int capacity();


    /**
     * Returns an list iterator that starts at the cursor position and can iterates forward and backward
     *
     * @return an forward and backward bidirectional list iterator that starts at the cursor position
     */
    public ListIterator<E> listIterator();


    /**
     * Returns a handle to  the element to the right of the cursor. The scroll does not change and the cursor does not change.
     *
     * @return a handle to the next element of the cursor without changing the cursor position or modifying the scroll
     * @throws IllegalStateException if the cursor is at the end of the scroll
     */
    public E getNext() throws IllegalStateException;


    /**
     * Returns a handle to  the element to the left of the cursor. The scroll does not change and the cursor does not change.
     *
     * @return a handle to the previous element of the cursor without changing the cursor position or modifying the scroll
     * @throws IllegalStateException if the cursor is at the beginning of the scroll
     */
    public E getPrevious() throws IllegalStateException;

    /**
     * Replaces the element to the right of the cursor and returns the original element
     *
     * @param element
     * @return the original element which is replaced by the input element.
     */
    public E replace(E element);


    public void splice(Scroll<E> that);

    /**
     * Moves all the elements from the right of the cursor to the left of the cursor in reverse order.
     * The cursor of the current scroll must be at the beginning of the scroll when the method is called.
     * The cursor will be at the end of the current scroll when the call is complete.
     */
    public void reverse();

    /**
     * Creates a new instance of a scroll.
     * The new scroll has the same concrete type that "this" scroll does, and it also has the same capacity.
     * The newly created scroll instance is empty – it does not contain any elements.
     *
     * @return a new instance of Scroll with same type and capacity as of "this" Scroll
     */
    public Scroll<E> newInstance();

    /**
     * Checks for equality between the scroll and another object.
     *
     * @param o
     * @return boolean value based on the equality between the scroll and @param o.
     */
    public boolean equals(Object o);

    /**
     * Returns the hash code of the scroll.
     *
     * @return the hash code of the scroll.
     */
    public int hashCode();

    /**
     * Returns a string representation of the scroll.
     *
     * @return a string representation of the scroll.
     */
    public String toString();
}