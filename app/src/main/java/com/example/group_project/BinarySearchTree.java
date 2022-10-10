package com.example.group_project;

/**
 * Reference: We refer the code of this class is from the Lab code.
 */
public class BinarySearchTree<T extends Comparable<T>> extends Tree<T> {

    public BinarySearchTree(T value) {
        super(value);
        this.leftNode = new EmptyBST<>();
        this.rightNode = new EmptyBST<>();
    }

    public BinarySearchTree(T value, Tree<T> leftNode, Tree<T> rightNode) {
        super(value, leftNode, rightNode);
    }

    @Override
    public T min() {
        return (leftNode instanceof EmptyTree) ? value : leftNode.min();
    }

    @Override
    public T max() {
        return (rightNode instanceof EmptyTree) ? value : rightNode.max();
    }

    @Override
    public Tree<T> find(T input) {
        if (input == null)
            throw new IllegalArgumentException("You get the wrong input dude.");
        if (input.compareTo(value) > 0) {
            return rightNode.find(input);
        } else if(input.compareTo(value) < 0) {
            return leftNode.find(input);
        }
        return this;
    }

    @Override
    public BinarySearchTree<T> insert(T value) {
        if (value == null) {throw new IllegalArgumentException("You get the wrong input dude.");}
        if (value.compareTo(value) < 0) {
            BinarySearchTree output = new BinarySearchTree<>(value, leftNode.insert(value), rightNode);
            return output;
        }
        BinarySearchTree output2 = new BinarySearchTree<>(value, leftNode, rightNode.insert(value));
        return output2;

    }


    public static class EmptyBST<T extends Comparable<T>> extends EmptyTree<T> {
        @Override
        public Tree<T> insert(T value) {
            return new BinarySearchTree<T>(value);
        }
    }
}
