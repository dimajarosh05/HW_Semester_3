package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyConcurencyListSet <T> {

    private Node<T> head;
    private Node<T> tail;
    private Lock lock;

    public MyConcurencyListSet() {
        this.head = null;
        this.tail = null;
        this.lock = new ReentrantLock();
    }

    public void add(T value) {
        Node<T> node = new Node<>(value);
        node.lock();
        lock.lock();
        if (head == null) {
            head = node;
            tail = node;
            lock.unlock();
        } else {
            Node tmp = tail;
            tmp.setNext(node);
            tail = node;
            lock.unlock();
            node.setPrev(tmp);
        }
        node.unlock();
    }

    public boolean contains(T value) {
        lock.lock();
        if (head == null) {
            return false;
        }
        Node tmp = head;
        lock.unlock();
        Node next = head.getNext();
        while (next != null) {
            next = tmp.getNext();
            if (tmp.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
