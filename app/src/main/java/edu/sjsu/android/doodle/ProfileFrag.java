package edu.sjsu.android.doodle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ProfileFrag extends Fragment {
    final FragmentManager fragmentManager = getFragmentManager();
    private View view;
    Button doodles_btn;
    Fragment doodles_frag;
    TextView name;
    TextView numOfFriends;
    IndividualDoodle post;
    de.hdodenhof.circleimageview.CircleImageView image;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_frag, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doodles_btn = view.findViewById(R.id.doodles_btn);
        name = view.findViewById(R.id.user_name);
        image = view.findViewById(R.id.circle_image);
        numOfFriends = view.findViewById(R.id.numberOfFriends);

        name.setText(ParseUser.getCurrentUser().getUsername());

        image.setImageResource(R.drawable.dog);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        int count = 0;
        try {
            count = query.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        numOfFriends.setText((count - 1) + "");

        doodles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doodles_frag = new UserDoodles();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, doodles_frag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}

