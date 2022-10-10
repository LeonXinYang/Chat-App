package com.example.group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    GlobalData globalData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        globalData = (GlobalData) getApplicationContext();
        // Set Tab Layout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout) ;
        pager2 = (ViewPager2) findViewById(R.id.viewpager2);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm,getLifecycle());
        pager2.setAdapter(adapter);

        loadPosts();
        loadUsers();
        autoSignIn("1@qq.com","123456");

        setFirebase();



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // System.out.println(user.getEmail());



    }

    private void setFirebase(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        globalData.setFirebaseDatabase(firebaseDatabase);
        myRef = firebaseDatabase.getReference("Users");
        GlobalData.setMe(new User(10000,"User01"));
        myRef.child(GlobalData.getUser_firebase().getUid()).child("UserID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(String.class) != null){
                    String s = snapshot.getValue(String.class);
                    GlobalData.setMe(new User(Integer.parseInt(s),"User"+s));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef = firebaseDatabase.getReference("Users");
        myRef.child(GlobalData.getUser_firebase().getUid()).child("Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                GlobalData.getMe().following = new ArrayList<>();
                if (value == null){
                    GlobalData.getMe().following = new ArrayList<>();
                }else{
                    String[] followingList = value.split(";");
                    ArrayList<Integer> following = new ArrayList<>();
                    for (String each : followingList){
                        following.add(Integer.parseInt(each));
                    }
                    GlobalData.getMe().following = following;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadFromFirebase(){

    }

    private void loadUsers(){
        UserDataXML userDataXML = UserDataXML.getUserDataXMLInstance();
        userDataXML.setAssetManager(this.getAssets());
        userDataXML.loadData();
        GlobalData.setUsers(userDataXML.users);
    }


    private void loadPosts(){
        PostDataXML postDataXML = PostDataXML.getPostDataXMLInstance();
        postDataXML.setAssetManager(this.getAssets());
        postDataXML.loadData();
        GlobalData.setPosts(postDataXML.posts);
        GlobalData.setPosts_public(postDataXML.posts_public);
    }

    protected void autoSignIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Auto login with " + email,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });
        if (GlobalData.getUser_firebase() == null){
            GlobalData.setUser_firebase(mAuth.getCurrentUser());
        }

    }


}