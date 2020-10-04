package com.scrapper.html.ds;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A Bounded Priority Queue uses a Sorted Set internally to create a generic and limited Priority Queue given the comparator and maximum size of the values to be stored.
 * @param <E>
 */
public class BoundedPriorityQueue<E> implements Iterable<E> {
    private final SortedSet<E> mQueue;
    private final int mMaxSize;
    private final Comparator<? super E> mComparator;

    /**
     * Initializes {@link BoundedPriorityQueue}
     * @param comparator
     * @param maxSize
     */
    public BoundedPriorityQueue(Comparator<? super E> comparator,
                                int maxSize) {
        mQueue = new TreeSet<E>(comparator);
        mComparator = comparator;
        mMaxSize = maxSize;
    }

    /**
     * Use to add objects to the Queue.
     * If the queue is empty or less than max size, it will simply add to the queue.
     * If the queue is full, it will check the object with the last added object in the queue and if the object is found to be better in comparison than only keeps it.
     * @param obj
     * @return
     */
    public boolean add(E obj) {
        if (size() < mMaxSize) {
            return mQueue.add(obj);
        }
        E last = mQueue.last();
        if (mComparator.compare(obj, last) >= 0)
            return false; // worst element better
        if (!mQueue.add(obj))
            return false; // already contain element
        mQueue.remove(last);
        return true;
    }

    /**
     * Returns the size of the queue.
     * @return
     */
    public int size() {
        return mQueue.size();
    }

    @Override
    public Iterator<E> iterator() {
        return mQueue.iterator();
    }

    /**
     * Returns the array representation of the queue elements
     * @param <E>
     * @return
     */
    public <E> E[] toArray() {
        return (E[]) mQueue.toArray();
    }

    @Override
    public String toString() {
        return mQueue.toString();
    }
}
