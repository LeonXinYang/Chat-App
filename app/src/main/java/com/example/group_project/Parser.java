package com.example.group_project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {

    // for wrong operation to throw
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    Tokenizer tokenizer;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }


    //Core method for user to search the post from the search column
    //核心方法，用户在EditText输入String -> 变成包裹了Tokenizer的Parser -> Parser 用此方法输出包含了tag的所有Post的
    //Arraylist<INTEGER>， 其中 Integer是Post ID
    public static List<Post> searchPost(List<Post> posts,String searchContent){
        //First step: to load all post in PostDataXML
        /*
        //Pretend to load post; 最后需要删除,假装已有posts
        Post test1 = new Post(1,1,"2020","I love COMP2100 #good #COMPgogogo #2100cool! # ## #");
        Post test2 = new Post(2,1,"2020","I love COMP2100 #bad #COMPgogo #2100cool # ## #");
        Post test3 = new Post(3,1,"2020","I love COMP2100 #godgo #COMPgo #2100coo # ## #");
        allPost.posts = new ArrayList<>();
        allPost.posts.add(test1);
        allPost.posts.add(test2);
        allPost.posts.add(test3);

         */

//        for(Post each : posts){
//            ArrayList<String> tag = parseTag(each);
//            each.setTags(tag);
//        }

        //COMPLETE 现在还没写好load XML，写好后用allPost.loadData("filePath")直接读取来代替。上面30-40行内容
        ArrayList<Post> output = new ArrayList<>();
        ArrayList<String> check = parseSearch(searchContent);
        System.out.println(posts.get(0).getTags().toString());
        for(String each : check){
            for(Post eachPost : posts){
                if(eachPost.getTags().contains(each)){
                    output.add(eachPost);
                }
            }
        }
        return output;
    }


    //核心方法，给一个Post，解析帖子内容中的Tag，并存储到帖子中的tag field中。
//    public static ArrayList<String> parseTag(Post post){
//        Tokenizer postContent = new Tokenizer(post.getContent());
//        ArrayList<String> output = new ArrayList<>();
//        String tag = "";
//        while(postContent.hasNext()) {
//            if (postContent.getLastToken() == null || postContent.getLastToken().getType() != Token.Type.HASH) {
//                postContent.next();
//                continue;
//            }
//            if (postContent.getCurrentToken().getType() == Token.Type.CONTENT) {
//                tag = postContent.getLastToken().getToken() + postContent.getCurrentToken().getToken();
//                output.add(tag);
//                postContent.next();
//            }
//            else{
//                postContent.next();
//            }
//        }
//        return output;
//    }

    //核心方法，EditText输入搜索内容，如Tag #COMP2100good #HAHA，返回tag列表
    public static ArrayList<String> parseSearch(String searchContent){
        searchContent += " ";
        Tokenizer postContent = new Tokenizer(searchContent);
        ArrayList<String> output = new ArrayList<>();
        String tag = "";
        while(postContent.hasNext()) {
            if (postContent.getLastToken() == null || postContent.getLastToken().getType() != Token.Type.HASH) {
                postContent.next();
                continue;
            }
            if (postContent.getCurrentToken().getType() == Token.Type.CONTENT) {
                tag = postContent.getLastToken().getToken() + postContent.getCurrentToken().getToken();
                output.add(tag);
                postContent.next();
            }
            else{
                postContent.next();
            }
        }
        return output;
    }
}
