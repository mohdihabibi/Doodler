package edu.sjsu.android.doodle;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserDoodles extends FeedsFrag {

    @Override
    public void getAllThePost() {

        ParseQuery<IndividualDoodle> postQuery = new ParseQuery<IndividualDoodle>(IndividualDoodle.class);
        postQuery.include(IndividualDoodle.USER);
        postQuery.whereEqualTo(IndividualDoodle.USER , ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(IndividualDoodle.DATE);
        postQuery.findInBackground(new FindCallback<IndividualDoodle>() {
            @Override
            public void done(List<IndividualDoodle> posts, ParseException e) {

                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
