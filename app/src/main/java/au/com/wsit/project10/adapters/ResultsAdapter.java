package au.com.wsit.project10.adapters;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import au.com.wsit.project10.ui.AddTopicDialog;
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
        private ImageView topicAddButton;

        public ViewHolder(View itemView)
        {
            super(itemView);
            videoTitle = (TextView) itemView.findViewById(R.id.videoTitle);
            videoDescription = (TextView) itemView.findViewById(R.id.videoDescription);
            videoImageView = (ImageView) itemView.findViewById(R.id.resultImageView);
            topicAddButton = (ImageView)itemView.findViewById(R.id.topicAddButton);
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

            topicAddButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    v.setScaleX(0);
                    v.setScaleY(0);
                    v.animate().scaleX(1).scaleY(1).start();

                    // Start the dialog
                    showDialog(result.getVideoTitle(), result.getVideoDescription(), result.getVideoID(), result.getVideoUrl());

                }
            });
        }
    }

    private void showDialog(String videoName, String videoDescription, String videoID, String videoUrl)
    {
        Activity activity = (Activity) context;

        Bundle bundle = new Bundle();
        bundle.putString(Constants.VIDEO_ID, videoID);
        bundle.putString(Constants.VIDEO_DESCRIPTION, videoDescription);
        bundle.putString(Constants.VIDEO_TITLE, videoName);
        bundle.putString(Constants.VIDEO_IMAGE_URL, videoUrl);

        FragmentManager fm = activity.getFragmentManager();
        AddTopicDialog addTopic = new AddTopicDialog();
        addTopic.setArguments(bundle);
        addTopic.show(fm, "AddTopicDialog");
    }
}
