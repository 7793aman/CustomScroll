package boundedscroll;

import java.util.Stack;

public class StackScroll<E> extends AbstractScroll<E> {

    Stack<E> left, right;

    public StackScroll(int max) {
        super(max);
        left = new Stack<>();
        right = new Stack<>();
    }

    /**
     * Adds the specified element to the right of the cursor in this scroll.
     *
     * @param elem element to be added to this scroll
     * @throws IllegalArgumentException if the specified element is null
     */
    @Override
    public void insert(E elem) {
        if (elem == null) throw new IllegalArgumentException();
        if (right.size() + left.size() >= capacity()) throw new IllegalStateException();
        right.push(elem);
    }

    @Override
    public E delete() {
        if (right.isEmpty()) throw new IllegalStateException();
        return right.pop();
    }

    @Override
    public void advance() {
        if (right.isEmpty()) throw new IllegalStateException();
        left.push(right.pop());
    }

    @Override
    public void retreat() {
        if (left.isEmpty()) throw new IllegalStateException();
        right.push(left.pop());
    }

    @Override
    public void reset() {
        while (leftLength() != 0) {
            retreat();
        }
    }

    @Override
    public void advanceToEnd() {
        while (rightLength() != 0) {
            advance();
        }
    }

    @Override
    public void swapRights(Scroll<E> that) {
        if (that instanceof StackScroll<E>) {
            if(this.leftLength() + that.rightLength() > this.capacity()) throw  new IllegalStateException();
            if(that.leftLength() + this.rightLength() > that.capacity()) throw  new IllegalStateException();

            StackScroll<E> stackScroll = (StackScroll<E>) that;
            Stack<E> temp = this.right;
            this.right = stackScroll.right;
            stackScroll.right = temp;

        } else {
            super.swapRights(that);
        }
    }

    @Override
    public int leftLength() {
        return left.size();
    }

    @Override
    public int rightLength() {
        return right.size();
    }

    @Override
    public Scroll<E> newInstance() {
        return new StackScroll<E>(capacity());
    }

}
