package com.example.group_project;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;
public class UserLoadTest {
    @Test
    public void loadData(){
        UserDataXML testUser = new UserDataXML();
        //TODO 路径修改 需要重写
//        testUser.users = testUser.loadData("src/test/java/com/example/group_project/users.xml");
        String output1 = "42,306,327,423,527,542,709,";
        String[] list = output1.split(",");
        ArrayList<Integer> outputTest = new ArrayList<>();
        for(String each : list){
            outputTest.add(Integer.parseInt(each));
        }
        System.out.print(outputTest);
        System.out.print(testUser.users.get(0).allPosts);
        assertTrue(outputTest.equals(testUser.users.get(0).allPosts));
    }
}
