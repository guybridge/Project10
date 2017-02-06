package au.com.wsit.project10.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.model.Topic;
import au.com.wsit.project10.ui.TopicVideosActivity;
import au.com.wsit.project10.utils.Constants;
import rx.functions.Action1;

/**
 * Created by guyb on 28/01/17.
 */

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder> implements Action1<ArrayList<Topic>>
{
    private ArrayList<Topic> topics = new ArrayList<>();
    private Context context;

    public TopicsAdapter(Context context)
    {
        this.context = context;
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
                    // Open an activity to show the videos in the topics list
                    Intent videoTopics = new Intent(context, TopicVideosActivity.class);
                    videoTopics.putExtra(Constants.TOPIC_ID, topic.getTopicId());
                    videoTopics.putExtra(Constants.TOPIC_NAME, topic.getTopicName());
                    context.startActivity(videoTopics);
                }
            });
        }

    }
}
