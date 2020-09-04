import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    // construct an empty deque
    public Deque(){
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return n==0;
    }

    // return the number of items on the deque
    public int size(){
        return n;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty()){
            last = first;
        } else {
            oldFirst.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null) {
            throw  new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty()){
            throw  new NoSuchElementException();
        }
        Item item = first.item;
        n--;
        if(isEmpty()){
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        n--;
        if (isEmpty()){
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class Node{
        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item>{

        private Node cur = first;

        @Override
        public boolean hasNext() {
            return cur.next == null;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = cur.item;
            cur = cur.next;
            return  item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}