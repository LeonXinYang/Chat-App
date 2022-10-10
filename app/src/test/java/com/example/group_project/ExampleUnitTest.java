package com.example.group_project;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void AVLTreeTest() {
        AVLTree sub3 = new AVLTree(3, new AVLTree.EmptyAVL(), new AVLTree.EmptyAVL());
        AVLTree sub6 = new AVLTree(6, sub3, new AVLTree.EmptyAVL());
        AVLTree sub10 = new AVLTree(10, sub6, new AVLTree.EmptyAVL());
//        AVLTree mytree = sub10.rightRotate();
////        System.out.println(sub10);
////        System.out.println(mytree)
        AVLTree tree = new AVLTree(10).insert(6).insert(3);
        System.out.println(tree);
    }
    @Test
    public void AVLTreeTest2(){
        AVLTree test1 = new AVLTree(3);
        test1 = test1.insert(4);
        test1 = test1.insert(5);
        System.out.print(test1.toString());
        assertEquals(new AVLTree(3).insert(4).insert(5), test1);
    }
}