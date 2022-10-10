package com.example.group_project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Post {
    private Integer postID;
    private Integer userID;
    private String date; // The format should be like 20210920 < 20211001
    private String content;
    private String media; // Url of image or gif file.
    private ArrayList<String> tags;
    private ArrayList<Integer> likeThisPostUsers; // who like this post
    private Boolean isPublic = true;
    private Integer view = 0;

    // Constructor


    public Post(Integer postID, Integer userID, String date, String content, ArrayList<String> tags, Boolean isPublic, Integer view) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
        this.content = content;
        this.tags = tags;
        this.media = "";
        this.likeThisPostUsers = new ArrayList<Integer>();
        this.isPublic = isPublic;
        this.view = view;
    }

    public Post(Integer postID, Integer userID, String date, String content, Boolean isPublic, Integer view) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
        this.content = content;
        this.media = "";
        this.tags = Parser.parseSearch(content);
        this.likeThisPostUsers = new ArrayList<Integer>();
        this.isPublic = isPublic;
        this.view = view;
    }

    public Post(Integer postID, Integer userID, String date, String content, Boolean isPublic, Integer view, String media) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
        this.content = content;
        this.media = media;
        this.tags = Parser.parseSearch(content);
        this.likeThisPostUsers = new ArrayList<Integer>();
        this.isPublic = isPublic;
        this.view = view;
    }

    public Post(Integer postID, Integer userID, String date, String content, ArrayList<String> tags, Boolean isPublic, Integer view, String media) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
        this.content = content;
        this.media = media;
        this.tags = tags;
        this.isPublic = isPublic;
        this.view = view;
        this.likeThisPostUsers = new ArrayList<Integer>();
    }

    public Post(Integer postID, Integer userID, String date, String content, ArrayList<String> tags, ArrayList<Integer> likeThisPostUsers, Boolean isPublic, Integer view, String media) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
        this.content = content;
        this.media = media;
        this.tags = tags;
        this.likeThisPostUsers = likeThisPostUsers;
        this.isPublic = isPublic;
        this.view = view;
    }

    public Post(Integer postID, Integer userID, String date, String content) {
        this.postID = postID;
        this.userID = userID;
        this.date = date;
        this.content = content;
        this.media = "";
        this.tags = Parser.parseSearch(content);
        this.likeThisPostUsers = new ArrayList<Integer>();
    }

    //getter

    public Integer getPostID() {
        return postID;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<Integer> getLikeThisPostUsers() {
        return likeThisPostUsers;
    }

    public void addLikedUser(Integer userID){
        if (!likeThisPostUsers.contains(userID)){
            likeThisPostUsers.add(userID);
        }

    }

    public void removeLikedUser(Integer userID){
        if (likeThisPostUsers.contains(userID)){
            likeThisPostUsers.remove(likeThisPostUsers.indexOf(userID));
        }

    }

    public String getDate() {
        return date;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public Integer getView() {
        return view;
    }

    public String getMedia() {
        return media;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public boolean equal(Object object){
        if(object instanceof Post == false){
            return false;
        }
        else{
            Post post = (Post) object;
            if(post.getPostID().equals(this.postID)){
                if(post.getUserID().equals(this.userID)){
                    if(post.getContent().equals(this.content)){
                        if(post.getDate().equals(this.date)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    //Setter
    //We make this post to be private post //Date:10.16
    public void bePrivate(){
        this.isPublic = false;
        return;
    }
    //The post is viewed by another user // Date:10.16
    public void view(){
        this.view += 1;
        return;
    }

}
