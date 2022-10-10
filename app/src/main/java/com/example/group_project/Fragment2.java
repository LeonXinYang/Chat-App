package com.example.group_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment2 extends Fragment {
    private RecyclerView timeline;
    TimelineAdapter timelineAdapter;
    private Handler handler;
    static List<Post> posts;
    List<Post> posts_public;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_2, container, false);
        posts = GlobalData.getPosts();
        posts_public = GlobalData.getPosts_public();

        // Set up recycler view for the timeline
        timeline = (RecyclerView) view.findViewById(R.id.RecyclerView_Timeline);
        timelineAdapter = new TimelineAdapter(getContext(),posts_public);
        timeline.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        timeline.setAdapter(timelineAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        // With timer and handler, schedule a new random post and add to the post data.
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    oneMorePost();
                    System.out.println("one post added, now have "+posts.size()+" posts");
                }
            }
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=0;
                handler.sendMessage(message);
            }
        },0,10000);
    }


    public void oneMorePost(){
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
        Random randpost = new Random();
        Random randUser = new Random();
        Random id = new Random();
        String content = "";
        for(int j = 0; j < 3; j++){
            content += sentence.get(randpost.nextInt(sentence.size()));
        }
        ArrayList<String> saveTag = new ArrayList<String>(Arrays.asList(tag.get(randpost.nextInt(tag.size()))));
        content += saveTag.get(0);
        Integer i = posts.size()+1;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monstr = Integer.toString(month);
        if(month < 10){
            monstr = "0" + month;
        }
        String daystr = Integer.toString(day);
        if(day < 10){
            daystr = "0" + day;
        }
        String dates = year + monstr + daystr;
        boolean isPublic = true;
        if(day > 25){
            isPublic = false;
        }
        String media = url.get(randpost.nextInt(3));
        int uid = randUser.nextInt(100);
        posts.add(0,new Post(i,uid,dates, content,saveTag, isPublic,1,media));
        posts_public.add(0,new Post(i,uid,dates, content,saveTag, isPublic,1,media));
        timelineAdapter.notifyDataSetChanged();

    }


}