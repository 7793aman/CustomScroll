package boundedscroll;

import java.util.ArrayList;
import java.util.List;

public class ListScroll<E> extends AbstractScroll<E> {
    private List<E> elements;
    private int pos;

    public ListScroll(int max) {
        super(max);
        elements = new ArrayList<E>();
    }

    @Override
    public void insert(E elem) {
        if (elem == null) throw new IllegalArgumentException();
        if (elements.size() == capacity()) throw new IllegalStateException();
        elements.add(pos, elem);
    }

    @Override
    public E delete() {
        if (rightLength() == 0) throw new IllegalStateException();
        return elements.remove(pos);
    }

    @Override
    public void advance() {
        if (rightLength() == 0) throw new IllegalStateException();
        pos = pos + 1;
    }

    @Override
    public void retreat() {
        if (leftLength() == 0) throw new IllegalStateException();
        pos = pos - 1;
    }

    @Override
    public void reset() {
        pos = 0;
    }

    @Override
    public void advanceToEnd() {
        pos = elements.size();
    }

    @Override
    public int leftLength() {
        return pos;
    }

    @Override
    public int rightLength() {
        return elements.size() - leftLength();
    }

    @Override
    public Scroll<E> newInstance() {
        return new ListScroll<E>(capacity());
    }

    @Override
    public int capacity() {
        return super.capacity();
    }

    @Override
    public void swapRights(Scroll<E> that) {
        super.swapRights(that);
    }

}
