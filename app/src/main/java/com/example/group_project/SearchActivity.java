package com.example.group_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends Fragment {
    private RecyclerView timeline_search;
    TimelineAdapter timelineAdapter;
    ImageButton imageButton_Search;
    GlobalData globalData;
    EditText editText_Search;
    List<Post> posts;
    Switch switch1;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.search_activity, container, false);
        imageButton_Search = (ImageButton) view.findViewById(R.id.imageButton_Search);
        editText_Search = (EditText) view.findViewById(R.id.editText_Search);

        final GlobalData globalData = (GlobalData)  getActivity().getApplicationContext();
        posts = Collections.emptyList();


        timeline_search = (RecyclerView) view.findViewById(R.id.RecyclerView_Timeline_Searched);
        timelineAdapter = new TimelineAdapter(getContext(),posts);
        timeline_search.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        timeline_search.setAdapter(timelineAdapter);

        switch1 = (Switch) view.findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    System.out.println(GlobalData.getMe().getFollowers().toString());
                    posts = PostDataXML.showFollowerPost(GlobalData.getMe().getFollowing());
                    timelineAdapter.setPosts(posts);
                    timelineAdapter.notifyDataSetChanged();
                }else{
                    posts.clear();
                    timelineAdapter.setPosts(posts);
                    timelineAdapter.notifyDataSetChanged();
                }
            }
        });

        imageButton_Search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                posts = GlobalData.getPosts();
                posts = Parser.searchPost(posts,editText_Search.getText().toString());
                timelineAdapter.setPosts(posts);
                timelineAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}