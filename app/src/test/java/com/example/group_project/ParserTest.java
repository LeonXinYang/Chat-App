package com.example.group_project;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.firebase.database.ThrowOnExtraProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParserTest {
    @Test
    public void parseSearchTest(){
        Post test1 = new Post(1,1,"2020","I love COMP2100 #good #COMPgogogo #2100cool! # ## #");
        //ArrayList<String> output = Parser.parseTag(test1);
        ArrayList<String> answer = new ArrayList<>();
        answer.add("#good");
        answer.add("#COMPgogogo");
        answer.add("#2100cool!");
        assertEquals(answer,Parser.parseSearch(test1.getContent()));
    }
    @Test
    public void bigTest(){
        Post test1 = new Post(1,1,"2020","I love COMP2100 #good #COMPgogogo #2100cool! # ## #");
        Post test2 = new Post(1,1,"2020","I love COMP2100 #bad #COMPgogo #2100cool # ## #");
        Post test3 = new Post(1,1,"2020","I love COMP2100 #godgo #COMPgo #2100coo # ## #");
        Post test4 = new Post(1,1,"2020","I love COMP2100 #gd #COMP #2100co # ## #");
        Post test5 = new Post(1,1,"2020","I love COMP2100 #g #COM #2100c # ## #");
        Post test6 = new Post(1,1,"2020","I love COMP2100 #ood #CO #2100 # ## #");
        Post test7 = new Post(1,1,"2020","I love COMP2100 #od #C #210! # ## #");
        ArrayList<Post> posts = new ArrayList<>(Arrays.asList(test1,test2,test3,test4,test5,test6,test7));
        List<Post> output = new ArrayList<Post>();
        output = Parser.searchPost(posts,"#2100cool");
        assertEquals(1, output.size());
        assertEquals(test2,output.get(0));
        //System.out.print(output);
    }

    //TODO
//    @Test
//    public void searchTest(){
//        Set<Integer> output = Parser.SearchPost("hahah#good hahaoojej#COMPgogo goodjob #2100cool");
//        Set<Integer> check = new HashSet<>();
//        check.add(1);
//        check.add(2);
//        assertEquals(check,output);
//
}
