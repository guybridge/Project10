package au.com.wsit.project10.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.api.TopicHelper;
import au.com.wsit.project10.model.Topic;
import rx.functions.Action1;

/**
 * Created by guyb on 28/01/17.
 */

public class AddToTopicsAdapter extends RecyclerView.Adapter<AddToTopicsAdapter.ViewHolder> implements Action1<ArrayList<Topic>>
{
    private static final String TAG = AddToTopicsAdapter.class.getSimpleName();
    private ArrayList<Topic> topics = new ArrayList<>();
    private Context context;
    private String videoId;
    private String videoName;
    private String videoDescription;
    private String videoImageUrl;

    public AddToTopicsAdapter(Context context,String videoName, String videoDescription, String videoId, String videoImageUrl)
    {
        this.context = context;
        this.videoId = videoId;
        this.videoName = videoName;
        this.videoDescription = videoDescription;
        this.videoImageUrl = videoImageUrl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bindViewHolder(topics.get(position));
    }

    @Override
    public int getItemCount()
    {
        return topics.size();
    }

    @Override
    public void call(ArrayList<Topic> topics)
    {
        this.topics = topics;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView topicName;
        private TextView videoCount;

        public ViewHolder(View itemView)
        {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topicTextView);
            videoCount = (TextView) itemView.findViewById(R.id.videoCount);
        }

        private void bindViewHolder(final Topic topic)
        {

            topicName.setText(topic.getTopicName());
            videoCount.setText(String.valueOf(topic.getVideoCount()) + " videos");

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Add the clicked item to a topic
                    TopicHelper topicHelper = new TopicHelper(context);
                    topicHelper.addVideoToTopic(videoImageUrl, videoName, videoDescription, videoId, topic.getTopicId(), new TopicHelper.SaveCallback()
                    {
                        @Override
                        public void onSuccess()
                        {
                            Log.i(TAG, "Successully added " + videoId + " to " + topic.getTopicName());
                            Toast.makeText(context, "Added video to " + topic.getTopicName(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFail(String errorMessage)
                        {

                        }
                    });



                }
            });
        }

    }
}
