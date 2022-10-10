package com.example.group_project;

import java.util.LinkedList;
import java.util.List;
/**
 * Reference: We refer codes in this class from the Lab code.
 */

public abstract class Tree<T extends Comparable<T>> {
    public final T value;       // element stored in this node of the tree.
    public Tree<T> leftNode;    // less than the node.
    public Tree<T> rightNode;   // greater than the node.

    public Tree() {
        value = null;
    }

    public Tree(T value) {
        if (value == null)
            throw new IllegalArgumentException("You get the wrong input dude");
        this.value = value;
    }

    public Tree(T value, Tree<T> leftNode, Tree<T> rightNode) {
        // Ensure inputs are not null.
        if (value == null || leftNode == null || rightNode == null)
            throw new IllegalArgumentException("Inputs cannot be null");

        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public abstract T min();                     // Finds the minimum.

    public abstract T max();                     // Finds the maximum.

    public abstract Tree<T> find(T element);     // Finds the element and returns the node.

    public abstract Tree<T> insert(T element);   // Inserts the element and returns a new instance of itself with the new node.

    public int getHeight() {
        // Check whether leftNode or rightNode are EmptyTree
        int leftNodeHeight = leftNode instanceof EmptyTree ? 0 : 1 + leftNode.getHeight();
        int rightNodeHeight = rightNode instanceof EmptyTree ? 0 : 1 + rightNode.getHeight();
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + value +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }

    /**
      * List the elements of the tree with in-order
      */
    public List<T> inOrder() {
		return this.treeToListInOrder(this);
	}

    /**
     * Converts tree to list in-order. Helper method of inOrder.
     * @param tree to convert to list.
     * @return in-order list of tree values.
     */
	private List<T> treeToListInOrder(Tree<T> tree) {
		List<T> list = new LinkedList<>();

		// Recurse through left subtree.
		if (tree.leftNode.value != null) {
			list.addAll(treeToListInOrder(tree.leftNode));
		}

		// Add current node's value
		list.add(tree.value);

        // Recurse through left subtree.
		if (tree.rightNode.value != null) {
			list.addAll(treeToListInOrder(tree.rightNode));
		}

		return list;
	}
}
