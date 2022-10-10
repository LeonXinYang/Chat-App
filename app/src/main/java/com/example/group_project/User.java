package com.example.group_project;


import java.util.ArrayList;
import java.util.Arrays;

public class User {
    protected Integer userID;
    protected String userName;
    protected ArrayList<Integer> fans; // Integer to represent User ID
    protected ArrayList<Integer> allPosts; // Integer to represent Post ID
    protected ArrayList<Integer> following; // Integer to represent Following users' ID.
    protected Boolean isProfilePublic = true;
    // 初始空user
    public User(){

    }
    //Constructor


    public User(Integer userID, String userName, ArrayList<Integer> followers, ArrayList<Integer> allPosts, ArrayList<Integer> following) {
        this.userID = userID;
        this.userName = userName;
        this.fans = followers;
        this.allPosts = allPosts;
        this.following = following;
    }

    public User(Integer userID, String userName, ArrayList<Integer> followers, ArrayList<Integer> allPosts, ArrayList<Integer> following, Boolean isProfilePublic) {
        this.userID = userID;
        this.userName = userName;
        this.fans = followers;
        this.allPosts = allPosts;
        this.following = following;
        this.isProfilePublic = isProfilePublic;
    }

    //Constructor
    public User(Integer userID, String userName) {
        this.userID = userID;
        this.userName = userName;
        this.fans = new ArrayList<Integer>();
        this.allPosts = new ArrayList<Integer>();
        this.following = new ArrayList<Integer>();
    }

    public String followingToFirebase(){
        String result = "";
        for (Integer i : following){
            result = result + String.valueOf(i) + ";";
        }
        return result;
    }

    // getter

    public Boolean getProfilePublic() {
        return isProfilePublic;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Integer> getFollowers() {
        return fans;
    }

    public ArrayList<Integer> getAllPosts() {
        return allPosts;
    }

    public ArrayList<Integer> getFollowing() {
        return following;
    }

    public int followingNum(){
        return following.size();
    }

    public int fansNum(){
        return fans.size();
    }

    //Setter
    //To make the profile private
    public void bePrivateProfile(){
        this.isProfilePublic = false;
    }

    public void addUserFollowing(Integer userID){
        if (!following.contains(userID)){
            following.add(userID);
        }

    }

    public void addUserFans(Integer userID){
        if (!fans.contains(userID)){
            fans.add(userID);
        }

    }
}
