package com.example.group_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private final Context context;
    private List<Post> posts;

    public TimelineAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;

    }

    public void setPosts(List<Post> posts_para){
        posts = posts_para;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_timeline_post,parent,false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.TimelineViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Boolean isFollowed = false;
        User currentUser = null;
        String avatarUrl;
        String userName = "";
        String userID = posts.get(position).getUserID().toString();
        for (User user:GlobalData.getUsers()){
            if (String.valueOf(user.userID).equals(userID)){
                userName = user.getUserName();
                currentUser = user;
                break;
            }
        }
        holder.getPostUsername().setText(userName);
        holder.getPostDate().setText(posts.get(position).getDate().toString());
        holder.getPostContent().setText(posts.get(position).getContent());
        holder.getLikeNumber().setText(String.valueOf(posts.get(position).getLikeThisPostUsers().size())); // Get liked number
        holder.getPostID().setText("PostID:" + String.valueOf(posts.get(position).getPostID()));

        avatarUrl = "https://cdn.iconscout.com/icon/premium/png-64-thumb/" + String.valueOf(Integer.parseInt(userID)*128) + ".png";
        Glide.with(context).load(avatarUrl).error(R.drawable.avatar_not_found).into(holder.getPostAvatar());
        Glide.with(context).load(posts.get(position).getMedia()).into(holder.getMedia());
        holder.chipGroup.removeAllViews();

        //Follow click event
        User finalCurrentUser1 = currentUser;
        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalData.setCurrentUser(finalCurrentUser1);
                GlobalData.getMe().following.add(Integer.parseInt(userID));
                GlobalData.getFirebaseDatabase().getReference("Users")
                        .child(GlobalData.getUser_firebase().getUid())
                        .child("Following").setValue(GlobalData.getMe().followingToFirebase());
                GlobalData.getCurrentUser().addUserFans(GlobalData.getMe().userID);
                holder.getFollow().setText("FOLLOWING");
                holder.getFollow().setTextColor(0xFFFFEB3B);
                holder.getFollow().setClickable(false);
            }
        });

        //If followed
        for (Integer i : GlobalData.getMe().following){
            if (String.valueOf(i).equals(userID)){
                isFollowed = true;
                break;
            }else{
                isFollowed = false;
            }
        }

        if (isFollowed){
            holder.getFollow().setText("FOLLOWING");
            holder.getFollow().setTextColor(0xFFFFEB3B);
            holder.getFollow().setClickable(false);
        }else{
            holder.getFollow().setText("FOLLOW");
            holder.getFollow().setTextColor(0xFFFFFFFF);
            holder.getFollow().setClickable(true);
        }


        for (String tag : posts.get(position).getTags()){
            addChip(holder.chipGroup, tag);
        }


        User finalCurrentUser = currentUser;
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalCurrentUser.getProfilePublic()){
                    GlobalData.setCurrentUser(finalCurrentUser);
                    Intent intent = new Intent(context,UserProfile.class);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context,"The user's profile is private.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.isLiked){
                    holder.isLiked = true;
                    holder.like.setImageResource(R.drawable.good_pressed);

                    //like + 1
                    UserPostInteractFacade.likeThisPost(GlobalData.getMe(),posts.get(position));
                    holder.getLikeNumber().setText(String.valueOf(posts.get(position).getLikeThisPostUsers().size())); // Refresh liked number

                }else{
                    holder.isLiked = false;
                    holder.like.setImageResource(R.drawable.good_normal);
                    posts.get(position).removeLikedUser(GlobalData.getMe().getUserID());
                    holder.getLikeNumber().setText(String.valueOf(posts.get(position).getLikeThisPostUsers().size())); // Refresh liked number
                }
            }
        });

    }

    public void addChip(ChipGroup chipGroup,String text){
        LayoutInflater inflater = LayoutInflater.from(context);
        Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip,chipGroup, false);
        newChip.setText(text);
        chipGroup.addView(newChip);
    }


    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public static class TimelineViewHolder extends RecyclerView.ViewHolder{
        private final ImageView avatar;
        private final TextView username;
        private final TextView date;
        private final TextView content;
        private final ImageView media;
        private final TextView likeNumber;
        private final ImageView like;
        private final ChipGroup chipGroup;
        private final Button follow;
        private final TextView postID;
        private Boolean isLiked;



        public TimelineViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.imageView_avatar_post);
            username = (TextView) itemView.findViewById(R.id.textView_username);
            date = (TextView) itemView.findViewById(R.id.textView_date);
            content = (TextView) itemView.findViewById(R.id.textView_content);
            media = (ImageView) itemView.findViewById(R.id.imageView_media);
            likeNumber = (TextView) itemView.findViewById(R.id.textView_like_number);
            like = (ImageView) itemView.findViewById(R.id.imageView_Like);
            chipGroup = (ChipGroup) itemView.findViewById(R.id.ChipGroup_tags);
            postID = (TextView) itemView.findViewById(R.id.textView_PostID);
            follow = (Button) itemView.findViewById(R.id.button_follow);
            isLiked = false;
        }

        public Button getFollow() {
            return follow;
        }
        public ImageView getPostAvatar() {
            return avatar;
        }

        public ChipGroup getChipGroup(){return chipGroup;}

        public TextView getPostUsername() {
            return username;
        }

        public TextView getPostDate() {
            return date;
        }

        public TextView getPostContent() {
            return content;
        }

        public TextView getPostID() {
            return postID;
        }

        public ImageView getMedia(){return media;}

        public TextView getLikeNumber(){return likeNumber;}

        public ImageView getLike(){return like;}

    }
}
