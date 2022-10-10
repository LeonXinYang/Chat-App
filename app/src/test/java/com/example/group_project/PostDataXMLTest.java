package com.example.group_project;

import org.junit.Test;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
public class PostDataXMLTest {
    @Test
    //Do not use this test anymore
    public void testLoad(){
        PostDataXML allPost = PostDataXML.getPostDataXMLInstance();
        Post test1 = new Post(4,1,"20200901","I love COMP2100 #good #COMPgogogo #2100cool! # ## #", true, 10);
        Post test2 = new Post(5,1,"20200901","I love COMP2100 #bad #COMPgogo #2100cool # ## #", true, 10);
        Post test3 = new Post(6,1,"20200901","I love COMP2100 #godgo #COMPgo #2100coo # ## #",true, 10);
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(test1);
        posts.add(test2);
        posts.add(test3);
        for(int i = 0; i< posts.size(); i++){
            System.out.print(allPost.posts.get(i).getTags());
            assertTrue(allPost.posts.get(i).equal(posts.get(i)));
        }

    }

    @Test
    public void testSave(){
        Post test1 = new Post(4,1,"20200901","I love COMP2100 #good #COMPgogogo #2100cool! # ## #",true, 10);
        Post test2 = new Post(5,1,"20200901","I love COMP2100 #bad #COMPgogo #2100cool # ## #", true, 10);
        Post test3 = new Post(6,1,"20200901","I love COMP2100 #godgo #COMPgo #2100coo # ## #", true, 10);
        PostDataXML allPost = PostDataXML.getPostDataXMLInstance();
        allPost.posts.add(test1);
        allPost.posts.add(test2);
        allPost.posts.add(test3);
        allPost.savaData("posts.xml:1");
        assertEquals("haha","haha");
    }

    //Core method!!!! To random Create 1000 posts and 100 users.
    @Test
    public void thousandPost(){
        ArrayList<String> tag = new ArrayList<String>(Arrays.asList("#Comp2100isGood", "#Comp6442isGood",
                "#DailyLife", "#Ilove2100","#HappyFriday","#JavaisCool","#PythonisCool","#Comp2100","#Comp6442","#Dinner","#DelicousFood"));
        ArrayList<String> sentence = new ArrayList<String>(Arrays.asList("I love comp2100. ","I love comp6442. ","I feel sad. ","Love is important. ",
                "Come on Sweetheart. ","That is my daily life. ","I always feel good. ","ANU is all the best. ","I love my hometown, Vancouver. ",
                "Glad to here that you love this place. ","Waterfall is the most beautiful scene I have ever seen in the Canada. ","I want to reject him, but he stops me.",
                "I promise that I will be a scientist in the future. ","My today's dinner will be sushi again.","If you want me to recommend a film for you, I would say Atlas. ",
                "Do you know who is my favourite poet? Alexander Pope absolutely. ", "I love the scene here. ", "The best place I have ever been is the Seattle. ","Because I win the competition here. ",
                "Because I meet my soulmate here. ","The Television power in Seattle, is called the needle tower. ","It is my adventure to meet you in the Charlotte. ", "How could I stay here forever.",
                "Experience for the first time is full of unknown and mystery. ","The past is always the best"));
        ArrayList<String> url = new ArrayList<String>(Arrays.asList("https://uploads0.wikiart.org/00261/images/hans-andersen-brendekilde/h-a-brendekilde-j-geren-1887.jpg!Large.jpg",
                "https://uploads0.wikiart.org/00165/images/erik-bulatov/train.jpg!Large.jpg","https://uploads6.wikiart.org/images/jack-bush/ward-sketch-1929.jpg!Large.jpg"));
        PostDataXML allPost = PostDataXML.getPostDataXMLInstance();
        Random randpost = new Random();
//        Post test1 = new Post(4,1,"20200901","I love COMP2100 #good #COMPgogogo #2100cool! # ## #",true, 10);
//        Post test2 = new Post(5,1,"20200901","I love COMP2100 #bad #COMPgogo #2100cool # ## #", true, 10);
//        Post test3 = new Post(6,1,"20200901","I love COMP2100 #godgo #COMPgo #2100coo # ## #", true, 10);
        for(int i = 1; i < 1050; i++){
            Random year = new Random();
            Random month = new Random();
            Random date = new Random();
            String content = "";
            for(int j = 0; j < 3; j++){
                content += sentence.get(randpost.nextInt(sentence.size()));
            }
            ArrayList<String> saveTag = new ArrayList<String>(Arrays.asList(tag.get(randpost.nextInt(tag.size()))));
            content += saveTag.get(0);
            Integer years = 2018+year.nextInt(3);
            Integer months = 1+month.nextInt(12);
            String monstr = months.toString();
            if(months < 10){
                monstr = "0" +months.toString();
            }
            Integer days = 1+date.nextInt(29);
            String daystr = days.toString();
            if(days < 10){
                daystr = "0" +days.toString();
            }
            String dates = years.toString() + monstr + daystr;
            Boolean isPublic = true;
            if(days > 25){
                isPublic = false;
            }
            String media = url.get(randpost.nextInt(3));

            allPost.posts.add(new Post(i,1+date.nextInt(100),dates, content,saveTag, isPublic,1,media));
        }
        allPost.savaData("src/test/java/com/example/group_project/posts.xml");
        UserDataXML testUser = UserDataXMLTest.add1000ValidUser();
        for(Post post : allPost.posts){
            testUser.users.get(post.getUserID()-1).allPosts.add(post.getPostID());
        }
        testUser.savaData("src/test/java/com/example/group_project/users.xml");
    }


    @Test
    public void testSize(){
        PostDataXML a= PostDataXML.getPostDataXMLInstance();
        a.loadData();
        System.out.print(a.posts.size());
        assertTrue(a.posts.size() > 1000);
    }
}
