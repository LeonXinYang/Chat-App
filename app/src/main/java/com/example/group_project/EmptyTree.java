package com.example.group_project;

/**
 * Reference: We refer some codes in this class from the Lab code.
 */
public abstract class EmptyTree<T extends Comparable<T>> extends Tree<T> {
    public abstract Tree<T> insert(T value);

    @Override
    public T min() {
        return null;
    }

    @Override
    public T max() {
        return null;
    }

    @Override
    public Tree<T> find(T value) {
        return null;
    }

    @Override
    public int getHeight() {
        return -1;
    }

    @Override
    public String toString() {
        return "{}";
    }

}