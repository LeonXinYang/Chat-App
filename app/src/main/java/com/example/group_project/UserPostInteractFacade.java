package com.example.group_project;

public class UserPostInteractFacade {
    /* Design Pattern: Facade
    This Design Pattern is to group the function between subsystem of User and Post,
    trying to create some methods for their interaction and complex behaviour.
     */

    /**
     * This is a method to make userA follow userB. The change should be happened in userDataXML
     * @param userDataXML
     * @param userAid
     * @param userBid
     */
    public static void userFollow(UserDataXML userDataXML, Integer userAid, Integer userBid){
        //TODO
        userDataXML.SetFollows(userAid,userBid);
    }

    /**
     * This is a method to make userA like postA. The change should happen in the postDataXML
     * @param user
     * @param post
     */
    public static void likeThisPost( User user, Post post){
        //TODO
        PostDataXML.SetUserFollow(user,post);
    }

    /**
     * This method is to return the total view of UserA.
     * @param user
     * @return
     */
    public static Integer totalViewOfUserA(PostDataXML postDataXML, UserDataXML userDataXML, User user){
        //TODO
        return postDataXML.getViews(user.getUserID());
    }

    /**
     * This method is to return the total like of UserA
     * @param user
     * @return
     */
    public static Integer totalLikeOfUserA(User user){
        //TODO
        return PostDataXML.getPostsLike(user.getUserID());
    }

    /**
     * This method is to return the total fans of UserA
     * @param userDataXML
     * @param user
     * @return
     */
    public static Integer totalFansOfUserA(UserDataXML userDataXML, User user){
        //TODO

        return userDataXML.getFans(user.getUserID());
    }
}
