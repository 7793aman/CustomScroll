package boundedscroll;

public class LinkedScroll<E> extends AbstractScroll<E> {

   private class Node{
    E contents;
    Node next;
    Node prev;
    public  Node(E contents){
        this.contents=contents;
    }
}
    Node guard;
    Node cursor;

    public LinkedScroll(int max) {
        super(max);
        cursor = new Node(null);
        guard = new Node(null);
        cursor.next=guard;
        cursor.prev=guard;
        guard.prev=guard;
        guard.next=guard;
    }

    @Override
    public void insert(E elem) {
        if (elem == null) throw new IllegalArgumentException();
        if (leftLength() + rightLength() >= capacity()) throw new IllegalStateException();
        Node newNode = new Node(elem);
        newNode.next = cursor.next;
        newNode.prev = cursor.prev;
        cursor.prev.next = newNode;
        cursor.next.prev = newNode;
        cursor.next = newNode;
    }

    @Override
    public E delete() {
       if(cursor.next == guard) throw  new IllegalStateException();
        Node toBeDeleted = cursor.next;
        toBeDeleted.prev.next = toBeDeleted.next;
        toBeDeleted.next.prev = toBeDeleted.prev;
        cursor.next=toBeDeleted.next;
        toBeDeleted.next=null;
        toBeDeleted.prev=null;
        return toBeDeleted.contents;
    }

    @Override
    public void advance() {
        if(cursor.next == guard) throw new IllegalStateException();
        Node temp = cursor.next;
        cursor.next = cursor.next.next;
        cursor.prev = temp;
    }

    @Override
    public void retreat() {
        if(cursor.prev == guard) throw new IllegalStateException();
        Node temp = cursor.prev;
        cursor.next= cursor.prev;
        cursor.prev = temp.prev;
    }

    @Override
    public void reset() {
       cursor.next=guard.next;
       cursor.prev=guard;
    }

    @Override
    public void advanceToEnd() {
        cursor.next=guard;
        cursor.prev=guard.prev;
    }

    @Override
    public int leftLength() {
        int count=0;
        Node temp =cursor;
        while(temp.prev !=guard){
            count+=1;
            temp = temp.prev;
        }
        return count;
    }

    @Override
    public int rightLength() {
        int count=0;
        Node temp =cursor;
        while(temp.next !=guard){
            count+=1;
            temp= temp.next;
        }
        return count;
    }

    @Override
    public Scroll<E> newInstance() {
        LinkedScroll<E> linkScroll = new LinkedScroll<>(capacity());
        return linkScroll;
    }

    @Override
    public void swapRights(Scroll<E> that) {
        super.swapRights(that);
    }
}
