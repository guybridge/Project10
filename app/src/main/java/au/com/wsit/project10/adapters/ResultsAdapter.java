package au.com.wsit.project10.adapters;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.model.Result;
import au.com.wsit.project10.ui.PlayVideoActivity;
import au.com.wsit.project10.utils.Constants;

import static android.content.ContentValues.TAG;

/**
 * Created by guyb on 28/12/16.
 */

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Result> results = new ArrayList<>();

    public ResultsAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResultsAdapter.ViewHolder holder, int position)
    {
        holder.bindViewHolder(results.get(position));
    }

    public void swap(ArrayList<Result> results)
    {
        if(results != null)
        {
            this.results = results;
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount()
    {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView videoTitle;
        private TextView videoDescription;
        private ImageView videoImageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            videoTitle = (TextView) itemView.findViewById(R.id.videoTitle);
            videoDescription = (TextView) itemView.findViewById(R.id.videoDescription);
            videoImageView = (ImageView) itemView.findViewById(R.id.resultImageView);
        }

        private void bindViewHolder(final Result result)
        {
            videoTitle.setText(result.getVideoTitle());
            videoDescription.setText(result.getVideoDescription());
            Picasso.with(context).load(result.getVideoUrl()).into(videoImageView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Start an intent to the player detail activity
                    Intent intent = new Intent(context, PlayVideoActivity.class);
                    intent.putExtra(Constants.VIDEO_TITLE, result.getVideoTitle());
                    intent.putExtra(Constants.VIDEO_DESCRIPTION, result.getVideoDescription());
                    intent.putExtra(Constants.VIDEO_ID, result.getVideoID());

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                    {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context, videoImageView, context.getString(R.string.imageTransition));
                        context.startActivity(intent, options.toBundle());
                    }
                    else
                    {
                        context.startActivity(intent);
                    }

                }
            });
        }
    }
}
