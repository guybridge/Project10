package au.com.wsit.project10.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.model.Result;

/**
 * Created by guyb on 28/12/16.
 */

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Result> results;

    public ResultsAdapter(Context context, ArrayList<Result> results)
    {
        this.context = context;
        this.results = results;
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
        private TextView speakerName;
        private TextView speakerComment;

        public ViewHolder(View itemView)
        {
            super(itemView);
            speakerName = (TextView) itemView.findViewById(R.id.speakerName);
            speakerComment = (TextView) itemView.findViewById(R.id.commentPreview);
        }

        private void bindViewHolder(Result result)
        {
            speakerName.setText(result.getSpeakerName());
            speakerComment.setText(result.getSampleComment());

            // TODO: Setup highlighting
        }
    }
}
