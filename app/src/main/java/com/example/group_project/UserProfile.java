package com.example.group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserProfile extends AppCompatActivity {
    ImageView imageView_avatar;
    TextView textView_username;
    TextView textView_userID;
    TextView textView_fans;
    TextView textView_posts;
    TextView textView_following;
    TextView textView_liked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imageView_avatar = (ImageView) findViewById(R.id.imageView_avatar_profile);
        textView_username = (TextView) findViewById(R.id.textView_username_profile);
        textView_fans = (TextView) findViewById(R.id.textView_fans_profile);
        textView_posts = (TextView) findViewById(R.id.textView_post_profile);
        textView_following = (TextView) findViewById(R.id.textView_following_profile);
        textView_liked= (TextView) findViewById(R.id.textView_Liked_profile);

        textView_userID = (TextView) findViewById(R.id.textView_userID_profile);
        String avatarUrl = "https://cdn.iconscout.com/icon/premium/png-128-thumb/" + String.valueOf(GlobalData.getCurrentUser().getUserID()*128) + ".png";
        Glide.with(this).load(avatarUrl).error(R.drawable.avatar_not_found).into(imageView_avatar);
        textView_username.setText(GlobalData.getCurrentUser().userName);
        textView_userID.setText(String.valueOf(GlobalData.getCurrentUser().userID));
        textView_fans.setText("Fans: " + String.valueOf(GlobalData.getCurrentUser().fansNum()));
        textView_liked.setText("Liked: " + String.valueOf(UserPostInteractFacade.totalLikeOfUserA(GlobalData.getCurrentUser())));
        textView_posts.setText("Posts: " + String.valueOf(GlobalData.getCurrentUser().getAllPosts().size()));
        textView_following.setText("Following: " + String.valueOf(GlobalData.getCurrentUser().followingNum()));
    }

}