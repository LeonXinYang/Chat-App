package com.example.group_project;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AVLTreeTest {

    @Test
    public void test_flatten(){
        AVLTree<Integer> new_avl = new AVLTree<>(5)
                .insert(1)
                .insert(2)
                .insert(3)
                .insert(4)
                .insert(6)
                .insert(7)
                .insert(8)
                .insert(9)
                .insert(10);
        List<Integer> list = new ArrayList<>();
        assertEquals("[4, 2, 1, 3, 8, 6, 5, 7, 9, 10]", new_avl.flatten(new_avl,list).toString());

    }

}