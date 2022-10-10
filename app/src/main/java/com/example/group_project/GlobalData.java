package com.example.group_project;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GlobalData extends Application {

    // A class to keep the global data that transferring between classes
    private static FirebaseDatabase firebaseDatabase;
    private static List<User> users;
    private static FirebaseUser me_firebase;
    private static User me;
    private static AVLTree<Integer> following_post_tree;
    private static AVLTree<Integer> following_users_tree;
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        GlobalData.currentUser = currentUser;
    }

    public static List<User> getUsers() {
        return users;
    }

    private static List<Post> posts;

    public static List<Post> getPosts_public() {
        return posts_public;
    }

    public static void setPosts_public(List<Post> posts_public) {
        GlobalData.posts_public = posts_public;
    }

    private static List<Post> posts_public;



    public static FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public static void setFirebaseDatabase(FirebaseDatabase firebaseDatabase_para) {
        firebaseDatabase = firebaseDatabase_para;
    }

    public static User getMe() {
        return me;
    }

    public static void setMe(User me_para) {
        me = me_para;
    }


    public static List<User> getUser() {
        return users;
    }

    public static void setUsers(List<User> user_para) {
        users = user_para;
    }

    public static FirebaseUser getUser_firebase() {
        return me_firebase;
    }

    public static void setUser_firebase(FirebaseUser user_firebase_para) {
        me_firebase = user_firebase_para;
    }

    public static List<Post> getPosts() {
        return posts;
    }

    public static void setPosts(List<Post> posts_para) {
        posts = posts_para;
    }

    public static void setFollowing_post_tree(AVLTree<Integer> fp_tree){
        for(User each:users)
            fp_tree.insert(each.getUserID());
        following_post_tree = fp_tree;

    }

    public static void setFollowing_users_tree(AVLTree<Integer> fu_tree){
        for(User each:users)
        fu_tree.insert(each.getUserID());
        following_users_tree = fu_tree;

    }

}
