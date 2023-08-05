package com.example.roomie.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roomie.R;
import com.example.roomie.model.MatchedUser;
import com.example.roomie.view.adapters.FollowingsAdapter;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView profilePic;
    private TextView username, profile_url, name, followings_count, followers_count, posts_count, total_points;
    private View posts_count_area, total_points_area, followers_count_area, followings_count_area;

    private RecyclerView rvPosts;
    private RecyclerView rvFollowings, rvFollowers;
    private FollowingsAdapter followingsAdapter;
    private ArrayList<MatchedUser> followings;

    //static User user = User.getInstance();


    //public String profilePicUrl = user.getImage();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profilePic = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);
        name = view.findViewById(R.id.name);

        followings_count = view.findViewById(R.id.followings_count);
        followings_count_area = view.findViewById(R.id.followings_count_area);

        rvFollowings = view.findViewById(R.id.rvFollowingsProfile);
        followings = new ArrayList<>();

        setProfileInfos();

        //loadProfilePic();

        LinearLayoutManager storiesLayoutManager = new LinearLayoutManager(getContext());
        storiesLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        populateFollowings();
        followingsAdapter = new FollowingsAdapter(getContext(), followings);
        StaggeredGridLayoutManager staggeredGridLayoutManager3 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        rvFollowings.setLayoutManager(staggeredGridLayoutManager3);
        rvFollowings.setAdapter(followingsAdapter);
        rvFollowings.setNestedScrollingEnabled(false);
    }

    /*private void loadProfilePic() {
        if (!isDetached() && getContext() != null) {
            Glide.with(getContext())
                    .load(profilePicUrl)
                    .into(profilePic);
        }
    }

     */

    private void setProfileInfos() {
        /*username.setText("@" + user.getUsername().replaceAll("\"",""));
        name.setText(user.getName().replaceAll("\"",""));
        posts_count.setText(String.valueOf(user.getPosts().size()));
        followers_count.setText(String.valueOf(user.getFollowers().size()));
        followings_count.setText(String.valueOf(user.getFollowings().size()));
        total_points.setText(String.valueOf((int) user.getTotalPoints()));
        profile_url.setText("www.mediar.com/" + user.getUsername());

         */

        followings_count_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvPosts.setVisibility(View.GONE);
                rvFollowers.setVisibility(View.GONE);
                rvFollowings.setVisibility(View.VISIBLE);

                followings_count_area.setBackgroundResource(R.drawable.page_modal);
                followers_count_area.setBackgroundResource(R.color.background_gray);
            }
        });
    }

    private void populateFollowings() {
        /*
        try {
            for (int i = 0; i < user.getFollowings().size(); i++) {

                JsonObject followingUser = (JsonObject) user.getFollowings().get(i);

                System.out.println("Following User  : " + followingUser.get("Name"));

                User following = new User();
                following.setName(String.valueOf(followingUser.get("Name")));
                following.setUsername(String.valueOf(followingUser.get("Username")));
                following.setImage(String.valueOf(followingUser.get("Image")));

                followings.add(following);
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getLocalizedMessage());
        }

         */
    }
}