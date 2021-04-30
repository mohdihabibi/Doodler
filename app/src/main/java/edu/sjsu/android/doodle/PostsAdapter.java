package edu.sjsu.android.doodle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<IndividualDoodle> posts;
    public PostsAdapter(Context context, List<IndividualDoodle> posts){
        this.context = context;
        this.posts = posts;
    }
    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.obj_post, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        IndividualDoodle post= posts.get(position);
        holder.bind(post);
    }
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<IndividualDoodle> post) {
        posts.addAll(post);
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private ImageView postedPic;
        private TextView description;
        private ImageView comment;
        private ImageView like;
        private ImageView share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.post_username);
            postedPic = itemView.findViewById(R.id.posted_picture);
            description = itemView.findViewById(R.id.post_description);
            comment = itemView.findViewById(R.id.post_comment);
            like = itemView.findViewById(R.id.post_like);
            share = itemView.findViewById(R.id.post_share);
        }

        public void bind(IndividualDoodle post){
            username.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(image.getUrl()).into(postedPic);
            }
            description.setText(post.getDescription());
        }
    }

}

