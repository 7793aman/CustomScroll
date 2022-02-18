package boundedscroll;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ScrollIterator<E> implements ListIterator<E> {

    Scroll<E> scroll;

    public ScrollIterator(Scroll<E> scroll){
        this.scroll=scroll;
    }
    @Override
    public boolean hasNext() {
        return  scroll.rightLength()!=0;
    }

    @Override
    public E next() {
        if(!this.hasNext()) throw  new NoSuchElementException();
        return scroll.getNext();
    }

    @Override
    public boolean hasPrevious() {
        return scroll.leftLength()!=0;
    }

    @Override
    public E previous() {
        if(!this.hasPrevious()) throw  new NoSuchElementException();
        return scroll.getPrevious();
    }

    @Override
    public int nextIndex() {
        return scroll.leftLength();
    }

    @Override
    public int previousIndex() {
        if(scroll.leftLength() == 0) return -1;
        return scroll.leftLength();
    }

    @Override
    public void remove() {
        //Not required
    }

    @Override
    public void set(E e) {
        //Not required
    }

    @Override
    public void add(E e) {
        //Not required
    }
}
