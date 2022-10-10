package com.example.group_project;

import java.util.ArrayList;
import java.util.List;

/**
 * Reference: We refer some of the codes are from the Lab and Xin Yang's Lab work.
 */
public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    //Constructor
    public AVLTree(T value, Tree<T> leftNode, Tree<T> rightNode) {
        super(value, leftNode, rightNode);
    }
    //Constructor
    public AVLTree(T value) {
        super(value);
        this.leftNode = new EmptyAVL<>();
        this.rightNode = new EmptyAVL<>();
    }

    /**
     * Reference: We refer the idea of the code is from the team members' Lab work.
     * @param element
     * @return
     */
    @Override
    public AVLTree<T> insert(T element) {
        if (element == null) {
            throw new IllegalArgumentException("You get a bad input which should not be null");
        }
        AVLTree output = new AVLTree(this.value);
        output.leftNode = this.leftNode;
        output.rightNode = this.rightNode;
        if(element.compareTo((T) output.value)> 0){
            Tree after = output.rightNode.insert(element);
            output.rightNode = after;
        }
        else if(element.compareTo((T) output.value)< 0){
            Tree after1 = output.leftNode.insert(element);
            output.leftNode = after1;
        }
        else{
            return output;
        }

        if(output.getBalanceFactor() == 2){
            AVLTree left = new AVLTree(output.leftNode.value, output.leftNode.leftNode,output.leftNode.rightNode);
            if(left.getBalanceFactor() == 1){
                return output.rightRotate();
            }
            if(left.getBalanceFactor() == -1){
                AVLTree step = left.leftRotate();
                output.leftNode = step;
                return output.rightRotate();
            }
        }
        if(output.getBalanceFactor() == -2){
            AVLTree right = new AVLTree(output.rightNode.value, output.rightNode.leftNode,output.rightNode.rightNode);
            if(right.getBalanceFactor() == -1){
                return output.leftRotate();
            }
            if(right.getBalanceFactor() == 1){
                AVLTree step = right.rightRotate();
                output.rightNode = step;
                return output.leftRotate();
            }
        }
        return output;
    }

    //This method is to complete the right rotate method for the tree when the balance factor is out of boundary
    public AVLTree<T> rightRotate() {
        Tree<T> motherNode = this.leftNode;
        return new AVLTree<T>(motherNode.value, motherNode.leftNode, new AVLTree<T>(value, motherNode.rightNode, this.rightNode));
    }

    //This method is to complete the left rotate method for the tree when the balance factor is out of boundary
    public AVLTree<T> leftRotate() {
        Tree<T> newMother = this.rightNode;
        return new AVLTree<T>(newMother.value, new AVLTree<T>(value,leftNode,newMother.leftNode), newMother.rightNode);
    }

    //This method is to get the Balance Factor of AVL tree
    public int getBalanceFactor() {

        return leftNode.getHeight() - rightNode.getHeight();
    }

    public static class EmptyAVL<T extends Comparable<T>> extends EmptyTree<T> {
        //This method is to build the empty AVL tree to
        // represent its empty when the left or the right sub tree is empty
        public AVLTree<T> insert(T tree) {
            return new AVLTree<T>(tree);
        }
    }

    public static void main(String[] args) {
        AVLTree sub3 = new AVLTree(3, new EmptyAVL(), new EmptyAVL());
        AVLTree sub6 = new AVLTree(6, sub3, new EmptyAVL());
        AVLTree sub10 = new AVLTree(10, sub6, new EmptyAVL());
//        AVLTree mytree = sub10.rightRotate();
////        System.out.println(sub10);
////        System.out.println(mytree)
        AVLTree tree = new AVLTree(10).insert(6).insert(3);
        System.out.println(tree);
    }

    public List<Integer> flatten(Tree<T> tree, List<Integer> list){
        if (tree.value != null){
            list.add((Integer) tree.value);
        }
        if (tree.leftNode == null){
            if (tree.rightNode == null){
                return list;
            } else {
                List<Integer> left_list = flatten(tree.rightNode, new ArrayList<>());
                list.addAll(left_list);
                return list;
            }
        } else {
            if (tree.rightNode == null){
                List<Integer> right_list = flatten(tree.leftNode, new ArrayList<>());
                list.addAll(right_list);
                return list;
            } else {
                List<Integer> left_list = flatten(tree.leftNode, new ArrayList<>());
                List<Integer> right_list = flatten(tree.rightNode, new ArrayList<>());
                list.addAll(left_list);
                list.addAll(right_list);
                return list;
            }
        }
    }
}
