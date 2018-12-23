import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
        public Node (Item a) {
            item = a;
            next = null;
            prev = null;
        }
    }

    private Node<Item> head = null;
    private Node<Item> tail = null;
    private int size = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> new_head = new Node<Item>(item);
        new_head.next = head;
        if(head != null) {
            head.prev = new_head;
        }
        else {
            tail = new_head;
        }
        head = new_head;
        size += 1;
    }

    public void addLast(Item item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> new_tail = new Node<Item>(item);
        new_tail.prev = tail;
        if(tail != null) {
            tail.next = new_tail;
        }
        else {
            head = new_tail;
        }
        tail = new_tail;
        size += 1;
    }

    public Item removeFirst() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Item v = head.item;
        Node<Item> new_head = head.next;
        if(new_head == null) {
            tail = null;
        }
        else {
            new_head.prev = null;
        }
        head = new_head;
        size -= 1;
        return v;
    }

    public Item removeLast() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Item v = tail.item;
        Node<Item> new_tail = tail.prev;
        if(new_tail == null) {
            head = null;
        }
        else {
            new_tail.next = null;
        }
        tail = new_tail;
        size -= 1;
        return v;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> cur = head;
        public boolean hasNext() {
            return cur != null;
        }
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
    }
}