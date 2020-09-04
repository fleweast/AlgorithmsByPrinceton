import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null){
            throw new IllegalArgumentException();
        }
        if (s.length == n) {
            resize(s.length * 2);
        }
        if(n == 0){
            s[n++] = item;
            return;
        }
        int randomIndex = StdRandom.uniform(n);
        Item temp = s[randomIndex];
        s[randomIndex] = item;
        s[n++] = temp;
    }

    private void resize(int c){
        Item[] copy = (Item[]) new Object[c];
        for (int i = 0; i < n; i++){
            copy[i] = s[i];
        }
        s = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        if (n == s.length / 4){
            resize(s.length / 2);
        }
        int randomIndex = StdRandom.uniform(n);
        Item item = s[randomIndex];
        s[randomIndex] = s[--n];
        s[n] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return s[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int[] randomIndices;
        private int i;
        public ArrayIterator() {
            i = 0;
            randomIndices = new int[n];
            for (int j = 0; j < n; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public Item next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            return s[randomIndices[i++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){

    }

}

