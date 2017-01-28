package au.com.wsit.project10.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.model.Topic;

/**
 * Created by guyb on 28/01/17.
 */

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder>
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

    public void swap(ArrayList<Topic> topics)
    {
        if(topics != null)
        {
            this.topics = topics;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount()
    {
        return topics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView topicName;

        public ViewHolder(View itemView)
        {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topicTextView);
        }

        private void bindViewHolder(Topic topic)
        {

            topicName.setText(topic.getTopicName());

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });
        }

    }
}
