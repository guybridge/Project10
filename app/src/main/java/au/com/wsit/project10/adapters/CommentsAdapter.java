package au.com.wsit.project10.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.model.Comment;
import rx.functions.Action1;

/**
 * Created by guyb on 28/01/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> implements Action1<ArrayList<Comment>>
{

    private Context context;
    private ArrayList<Comment> comments = new ArrayList<>();

    public CommentsAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bindViewHolder(comments.get(position));
    }

    @Override
    public int getItemCount()
    {
        return comments.size();
    }

    @Override
    public void call(ArrayList<Comment> comments)
    {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView authorName;
        private TextView authorComment;
        private ImageView authorImage;

        public ViewHolder(View itemView)
        {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.authorName);
            authorComment = (TextView) itemView.findViewById(R.id.authorComment);
            authorImage = (ImageView) itemView.findViewById(R.id.authorImageView);
        }

        public void bindViewHolder(Comment comment)
        {
            authorName.setText(comment.getAuthorName());
            authorComment.setText(comment.getAuthorComment());
            Picasso.with(context).load(comment.getAuthorImageUrl()).into(authorImage);
        }
    }
}
