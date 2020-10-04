package com.scrapper.html.ds;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class BoundedPriorityQueue<E> implements Iterable<E> {
    private final SortedSet<E> mQueue;
    private final int mMaxSize;
    private final Comparator<? super E> mComparator;

    public BoundedPriorityQueue(Comparator<? super E> comparator,
                                int maxSize) {
        mQueue = new TreeSet<E>(comparator);
        mComparator = comparator;
        mMaxSize = maxSize;
    }

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

    public int size() {
        return mQueue.size();
    }

    @Override
    public Iterator<E> iterator() {
        return mQueue.iterator();
    }

    public <E> E[] toArray() {
        return (E[]) mQueue.toArray();
    }

    @Override
    public String toString() {
        return mQueue.toString();
    }
}
