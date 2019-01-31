package com.xqh.study.algorithm.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayList 的实现
 * @param <AnyType>
 */
public class MyArrayList<AnyType> implements Iterable<AnyType> {

    private static final int DEFAULT_CAPACITY = 10;

    private int theSize;

    private AnyType[] theItems;

    public MyArrayList(){
        doClear();
    }

    public void clear(){
        doClear();
    }

    private void doClear() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public int size(){
        return theSize;
    }

    public boolean isEmpty(){
        return theSize == 0;
    }

    public void trimToSize(){
        ensureCapacity(size());
    }

    public AnyType get(int idx){
        if(idx < 0 || idx >= size())
            throw new ArrayIndexOutOfBoundsException();
        return theItems[idx];
    }

    public AnyType set(int idx, AnyType newVal){
        if(idx < 0 || idx >= size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        AnyType old = theItems[idx];
        theItems[idx] = newVal;
        return old;
    }

    public void ensureCapacity(int newCapacity) {
        if(newCapacity < theSize)
            return;
        AnyType[] old = theItems;

        theItems= (AnyType[]) new Object[newCapacity];
        for(int i=0;i<theSize;i++){
            theItems[i] = old[i];
        }
    }

    public boolean add(AnyType x){
        add(size(), x);
        return true;
    }

    public void add(int idx, AnyType x) {
        if(theItems.length == size()){
            ensureCapacity(size()*2 + 1);
        }
        theItems[idx] = x;
        theSize++;
    }

    public AnyType remove(int idx){
        AnyType removeItem = theItems[idx];
        for(int i = idx; i<size() - 1; i++){
            theItems[i] = theItems[i+1];
        }
        theSize--;
        return removeItem;
    }

    public Iterator<AnyType> iterator(){
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<AnyType>{

        private int current=0;
        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public AnyType next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return theItems[current++];
        }


        public void remove(){
            MyArrayList.this.remove(--current);
        }
    }

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(10);
        list.add(20);
        list.add(30);

    }

}
