package boundedscroll;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract  class AbstractScroll<E> implements Scroll<E> {

    private final int capacity;

    public AbstractScroll(int max){
        capacity=max;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ScrollIterator(this);
    }

    @Override
    public Iterator<E> iterator() {
        return new ScrollIterator(this);
    }

    @Override
    public E getNext() {
        if(this.rightLength() == 0) throw new NoSuchElementException();
        E result = delete();
        insert(result);
        return result;
    }

    @Override
    public E getPrevious() {
        if(this.leftLength() == 0) throw new NoSuchElementException();
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
        if(leftLength() != 0) throw new IllegalStateException();
        Scroll temp = this.newInstance();

        advanceToEnd();
        while (leftLength()!=0){
            temp.insert(getPrevious());
            temp.advance();
            retreat();
        }

        temp.reset();
        //swap right

        swapRights(temp);
    }

    /**
     *
     * [1,2,3,4,5]  [6,7,8,9,10,11]
     *    p	               p
     *
     * [1,2,10,11,5] [6,7,8,9,3,4]
     *          p               p
     * [1,2,8,9,10,11] [6,7,,3,4,5]
     *
     * [1,2,3,4][5,6,7]
     *
     * [8,9,10,11,12,13,14]
     *         p
     */
    @Override
    public void swapRights(Scroll<E> that){
        int thisLeftLength = this.leftLength();
        int thatLeftLength = that.leftLength();


        while (this.rightLength()>0 && that.rightLength()>0){
            E thatElement = that.getNext();
            E thisElement = this.replace(thatElement);
            that.replace(thisElement);
            this.advance();
            that.advance();
        }

        if(this.rightLength()>0){
            that.retreat();
            swapRightHelper(this,that,thisLeftLength,thatLeftLength);

        }else{
            this.retreat();
            swapRightHelper(that,this, thatLeftLength, thisLeftLength);

        }
    }

    private void swapRightHelper(Scroll<E> first, Scroll<E> second, int leftthisLen, int leftthatLen){
        while (first.rightLength()>0){
            E element = first.delete();
            second.insert(element);
            if(first.rightLength()==0) break;
            first.advance();
        }

        while(first.leftLength() != leftthisLen){
            first.retreat();
        }

        while(second.leftLength() != leftthatLen){
            second.retreat();
        }

    }

    public void splice(Scroll<E> that) {
        if(this.leftLength() + this.rightLength() +that.rightLength() > this.capacity())
            throw  new IllegalStateException();
        if(that.leftLength() > 0){
            throw new IllegalStateException();
        }
        if(that.rightLength() > 0){
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
        reset();
        int length = rightLength();
        sb.append("[");
        for(int i=0; i< length ; i++){
            E elem = getNext();
            sb.append(elem);
            if (i!=length-1) sb.append(" ");
            advance();
        }
        sb.append("]");
        return sb.toString();

    }


//    @Override
//    public boolean equals(Object o){
//        Scroll<E> that = (Scroll<E>) o;
//        if(this.capacity() == that.capacity() && this.leftLength() == that.leftLength() && this.rightLength() == that.rightLength()){
//            //left loop
//            while(this.leftLength()>0){
//                if(this.getPrevious() != that.getPrevious()){
//                    return  false;
//                }
//                this.retreat();
//                that.retreat();
//            }
//            while(this.rightLength()>=0){
//                if(this.getNext() != that.getNext()){
//                    return  false;
//                }
//                this.advance();
//                that.advance();
//            }
//            return  true;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode(){
//        int hash=0;
//        hash +=this.capacity();
//        hash+=this.rightLength();
//        hash+=this.leftLength();
//            //left loop
//
//        while(this.rightLength() >=0){
//            hash+= this.getNext().hashCode();
//            advance();
//        }
//
//        while(this.leftLength() >=0){
//            hash+= this.getPrevious().hashCode();
//            retreat();
//        }
//         return  hash;
//        }

}
