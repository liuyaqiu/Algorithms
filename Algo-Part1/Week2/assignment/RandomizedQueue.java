import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int capacity;
    private int size;

    public RandomizedQueue() {
        capacity = 8;
        size = 0;
        array = (Item[])new Object[capacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        if(size == capacity) {
            capacity = 2 * capacity;
            Item[] new_array = (Item [])new Object[capacity];
            for(int i = 0; i < size; i++) {
                new_array[i] = array[i];
            }
            array = new_array;
        }
        if(size <= capacity / 4) {
            capacity = capacity / 2 + 1;
            Item[] new_array = (Item [])new Object[capacity];
            for(int i = 0; i < size; i++) {
                new_array[i] = array[i];
            }
            array = new_array;
        }
    }

    public void enqueue(Item item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }
        resize();
        array[size] = item;
        size += 1;
    }

    public Item dequeue() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        Item tmp = array[index];
        if(index != size - 1) {
            array[index] = array[size - 1];
        }
        size -= 1;
        resize();
        return tmp;
    }

    public Item sample() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        return array[index];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] index;
        private int cur;
        private int length;

        RandomizedQueueIterator() {
            index = new int[size];
            for(int i = 0; i < size; i++) {
                index[i] = i;
            }
            StdRandom.shuffle(index);
            length = size;
            cur = 0;
        }

        public boolean hasNext() {
            return cur < length;
        }

        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = array[index[cur]];
            cur += 1;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
    }
}
