package boundedscroll;

import java.util.Iterator;
import java.util.ListIterator;

public abstract class AbstractScroll<E> implements Scroll<E> {

    private final int capacity;

    public AbstractScroll(int max) {
        capacity = max;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ScrollIterator<E>(this);
    }

    @Override
    public Iterator<E> iterator() {
        return new ScrollIterator<E>(this);
    }

    @Override
    public E getNext() {
        if (this.rightLength() == 0) throw new IllegalStateException();
        E result = delete();
        insert(result);
        return result;
    }

    @Override
    public E getPrevious() {
        if (this.leftLength() == 0) throw new IllegalStateException();
        retreat();
        E result = delete();
        insert(result);
        advance();
        return result;
    }

    @Override
    public E replace(E element) {
        if (element == null) throw new IllegalArgumentException();
        E result = delete();
        insert(element);
        return result;
    }

    @Override
    public void reverse() {
        if (leftLength() != 0) throw new IllegalStateException();
        Scroll<E> temp = this.newInstance();
        advanceToEnd();
        while (leftLength() != 0) {
            temp.insert(getPrevious());
            temp.advance();
            retreat();
        }
        temp.reset();
        swapRights(temp);
        advanceToEnd();
    }

    @Override
    public void swapRights(Scroll<E> that) {
        if (this.leftLength() + that.rightLength() > this.capacity() ||
                that.leftLength() + this.rightLength() > that.capacity()) {
            throw new IllegalStateException();
        }
        Scroll<E> temp = this.newInstance();
        swapRightHelper(that, temp);
    }

    private void swapRightHelper(Scroll<E> that, Scroll<E> temp) {
        while (this.rightLength() != 0) {
            temp.insert(this.delete());
        }
        while (that.rightLength() != 0) {
            this.insert(that.delete());
        }
        while (temp.rightLength() != 0) {
            that.insert(temp.delete());
        }

        int count = this.rightLength();
        this.advanceToEnd();
        while (count > 0) {
            this.retreat();
            temp.insert(this.delete());
            count--;
        }

        while (temp.rightLength() != 0) {
            this.insert(temp.delete());
        }
    }

    public void splice(Scroll<E> that) {
        if (this.leftLength() + this.rightLength() + that.rightLength() > this.capacity())
            throw new IllegalStateException();

        if (that.leftLength() > 0) {
            throw new IllegalArgumentException();
        }
        if (that.rightLength() > 0) {
            E element = that.delete();
            this.insert(element);
            this.advance();
            splice(that);
        }
    }

    //--------------------------------------------------
    //Object methods
    //--------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int leftLength = leftLength();
        int rightLength = rightLength();
        reset();
        sb.append("[");
        for (int i = 0; i < leftLength; i++) {
            E elem = getNext();
            advance();
            sb.append(elem);
            sb.append(i <= leftLength - 2 ? ", " : "");
        }

        sb.append("]");
        sb.append("[");
        for (int i = 0; i < rightLength; i++) {
            E elem = getNext();
            sb.append(elem);
            sb.append(i <= rightLength - 2 ? ", " : "");
            advance();
        }

        sb.append("]:");
        sb.append(capacity());

        while (this.leftLength() != leftLength) {
            this.retreat();
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        Scroll that = (Scroll) o;

        int l1 = this.leftLength();
        int l2 = that.leftLength();

        if (this.capacity() != that.capacity()) return false;
        if (this.leftLength() != that.leftLength()) return false;
        if (this.rightLength() != that.rightLength()) return false;

        this.reset();
        that.reset();

        boolean flag = true;
        while (this.rightLength() != 0) {
            if (this.getNext() != that.getNext()) {
                flag = false;
                break;
            }
            this.advance();
            that.advance();
        }
        this.reset();
        that.reset();
        for (int i = 0; i < l1; i++) this.advance();
        for (int i = 0; i < l2; i++) that.advance();
        return flag;
    }

    @Override
    public int hashCode() {
        int leftLength = this.leftLength();
        int result = 17;
        for (E element : this) {
            result = 31 * result + element.hashCode();
        }
        result = 31 * result + capacity();
        result = 31 * result + leftLength;
        result = 31 * result + this.rightLength();
        this.reset();
        for (int i = 1; i <= leftLength; i++) {
            this.advance();
        }
        return result;
    }

}
