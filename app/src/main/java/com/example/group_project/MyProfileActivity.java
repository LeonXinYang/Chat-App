package com.example.group_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

public class MyProfileActivity extends Fragment {
    GlobalData globalData;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_my_profile, container, false);
        Switch private_switch = view.findViewById(R.id.switch2);
        private_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    GlobalData.getMe().isProfilePublic = Boolean.FALSE;

                }else {
                    GlobalData.getMe().isProfilePublic = Boolean.TRUE;
                }
            }
        });
        User me = GlobalData.getMe();
        TextView post_num = view.findViewById(R.id.post_num);
        TextView follow_num = view.findViewById(R.id.follow_num);
        TextView fans_num = view.findViewById(R.id.fans_num);
        TextView view_num = view.findViewById(R.id.view_num);
        int postNumFromData = me.allPosts.size();
        int followNumFromData = me.followingNum();
        int fansNumFromData = me.fansNum();
        int viewNumFromData = 0;
        for(Post post : GlobalData.getPosts()){
            if (post.getUserID() == me.userID){
                viewNumFromData += post.getView();
            }
        }
        post_num.setText(postNumFromData);
        follow_num.setText(followNumFromData);
        fans_num.setText(fansNumFromData);
        view_num.setText(viewNumFromData);

        return view;
    }

}