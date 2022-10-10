package com.example.group_project;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;
import java.util.Random;

public class Fragment1 extends Fragment {
    private RecyclerView rand_bottle;
    static Integer randomPostID;
    List<Post> posts;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_1, container, false);
        final GlobalData globalData = (GlobalData)  getActivity().getApplicationContext();
        posts = globalData.getPosts();

        // Get a random bottle
        List<Post> rand_post = getRandomBottle();
        rand_bottle = (RecyclerView) view.findViewById(R.id.recyclerView_randbottle);
        TimelineAdapter timelineAdapter = new TimelineAdapter(getContext(),rand_post);
        rand_bottle.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rand_bottle.setAdapter(timelineAdapter);

        ImageButton button_DriftBottle = view.findViewById(R.id.button_DriftBottle);
        ImageButton imageButton_Close = view.findViewById(R.id.imageButton_Close);
        ImageView post_imageview = view.findViewById(R.id.imageview_Post);

        button_DriftBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set visibility
                post_imageview.setVisibility(View.VISIBLE);
                imageButton_Close.setVisibility(View.VISIBLE);
                rand_bottle.setVisibility(View.VISIBLE);

                // Update posts
                timelineAdapter.setPosts(getRandomBottle());
                timelineAdapter.notifyDataSetChanged();
            }
        });

        imageButton_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set invisibility
                post_imageview.setVisibility(View.INVISIBLE);
                imageButton_Close.setVisibility(View.INVISIBLE);
                rand_bottle.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // A help method to get a random post to display in drift bottle
    public List<Post> getRandomBottle(){
        Random random = new Random();
        randomPostID = random.nextInt(posts.size());
        List<Post> one_random = posts.subList(randomPostID,randomPostID+1);
        return one_random;
    }
}