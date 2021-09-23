package com.example.androidebook.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.example.androidebook.Item.CommentList;
import com.example.androidebook.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Activity activity;
    private List<CommentList> commentLists;

    public CommentAdapter(Activity activity, List<CommentList> commentLists) {
        this.activity = activity;
        this.commentLists = commentLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.comment_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (!commentLists.get(position).getUser_image().equals("")) {
            Picasso.get().load(commentLists.get(position).getUser_image())
                    .placeholder(R.drawable.profile).into(holder.circleImageView);
        }

        holder.textView_Name.setText(commentLists.get(position).getUser_name());
        holder.textView_date.setText(commentLists.get(position).getComment_date());
        holder.textView_comment.setText(commentLists.get(position).getComment_text());

    }

    @Override
    public int getItemCount() {
        if (commentLists.size() == 0) {
            return 0;
        } else if (commentLists.size() == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView textView_Name, textView_date, textView_comment;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.imageView_comment_adapter);
            textView_Name = itemView.findViewById(R.id.textView_userName_comment_adapter);
            textView_date = itemView.findViewById(R.id.textView_date_comment_adapter);
            textView_comment = itemView.findViewById(R.id.textView_comment_adapter);
        }
    }
}
