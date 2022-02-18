package boundedscroll;

import java.util.ArrayList;
import java.util.List;

public class ListScroll<E> extends AbstractScroll<E> {
    private List<E> elements;
    private int position;

    public ListScroll(int max) {
        super(max);
        elements  = new ArrayList<E>();
    }

    @Override
    public void insert(E elem) {
        if(leftLength() + rightLength() >= capacity()) throw new IllegalStateException();
        if(elem == null) throw  new IllegalArgumentException();
        elements.add(position,elem);
    }

    @Override
    public E delete() {
        if(position == capacity()) throw new IllegalStateException();
        return elements.remove(position);
    }

    @Override
    public void advance() {
        if(rightLength() ==0) throw new IllegalStateException();
        if(rightLength() ==0) throw new IllegalStateException();
        position = position +1;
    }

    @Override
    public void retreat() {
        if(leftLength()==0) throw new IllegalStateException();
        position -=1;
    }

    @Override
    public void reset() {
        position =0;
    }

    @Override
    public void advanceToEnd() {
        position = elements.size();
    }

    @Override
    public int leftLength() {
        return position;
    }

    @Override
    public int rightLength() {
        return elements.size()-(position);
    }

    @Override
    public Scroll<E> newInstance() {
        ListScroll<E> listScroll = new ListScroll<E>(capacity());
        return listScroll;
    }

    @Override
    public void swapRights(Scroll<E> that) {
            super.swapRights(that);
    }
}
