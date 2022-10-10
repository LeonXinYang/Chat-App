package com.example.group_project;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;
public class PostTest {
    @Test
    public void equalAndConstructor() {
        Integer postID = 1;
        Integer userID = 1;
        String date = "20200820";
        String content = "haha";
        String media = "www.google.com";
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<Integer> likeThisPostUsers = new ArrayList<>();
        Boolean isPublic = true;
        Integer view = 0;
        Post test1 = new Post(postID, userID, date,content,tags, isPublic, view);
        assertEquals(test1.getContent(), (content));
        assertEquals(test1.getTags(), tags);
        assertEquals(test1.getDate(), date);
        assertEquals(test1.getLikeThisPostUsers(),likeThisPostUsers);
        assertEquals(test1.getContent(), (content));
        assertTrue(test1.equal(new Post(postID, userID, date,content,tags, isPublic, view)));

    }
    @Test
    public void viewTest(){
        Integer postID = 1;
        Integer userID = 1;
        String date = "20200820";
        String content = "haha";
        String media = "www.google.com";
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<Integer> likeThisPostUsers = new ArrayList<>();
        Boolean isPublic = true;
        Integer view = 0;
        Post test1 = new Post(postID, userID, date,content,tags, isPublic, view);
        test1.view();
        assertTrue(test1.getView() == 1);
    }
}
