package edu.sjsu.android.doodle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedsFrag extends Fragment {
    RecyclerView rv;
    PostsAdapter adapter;
    List<IndividualDoodle> allPosts;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feeds_frag, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.feeds);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        getAllThePost();

    }
    public void getAllThePost() {
        ParseQuery<IndividualDoodle> query = new ParseQuery<IndividualDoodle>(IndividualDoodle.class);
        query.include(IndividualDoodle.USER);
        query.addDescendingOrder(IndividualDoodle.DATE);
        query.findInBackground(new FindCallback<IndividualDoodle>() {
            @Override
            public void done(List<IndividualDoodle> postList, ParseException e) {

                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                adapter.clear();
                adapter.addAll(postList);
            }
        });
    }


}
