package au.com.wsit.project10.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.adapters.AddToTopicsAdapter;
import au.com.wsit.project10.adapters.TopicsAdapter;
import au.com.wsit.project10.api.TopicHelper;
import au.com.wsit.project10.model.Topic;
import au.com.wsit.project10.utils.Constants;

/**
 * Created by guyb on 29/01/17.
 */

public class AddTopicDialog extends DialogFragment
{

    private RecyclerView topicsRecyclerView;
    private AddToTopicsAdapter addToTopicsAdapter;
    private TopicHelper topicHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dialog_add_to_topic, container, false);

        topicHelper = new TopicHelper(getActivity());

        Bundle bundle = getArguments();
        String videoId = bundle.getString(Constants.VIDEO_ID);
        String videoName = bundle.getString(Constants.VIDEO_TITLE);
        String videoDescription = bundle.getString(Constants.VIDEO_DESCRIPTION);
        String videoImageUrl = bundle.getString(Constants.VIDEO_IMAGE_URL);

        topicsRecyclerView = (RecyclerView) rootView.findViewById(R.id.topicsRecyclerView);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addToTopicsAdapter = new AddToTopicsAdapter(getActivity(),videoName, videoDescription, videoId, videoImageUrl);
        topicsRecyclerView.setAdapter(addToTopicsAdapter);

        getTopics();

        return rootView;
    }

    private void getTopics()
    {

        topicHelper.getTopics(new TopicHelper.GetCallback()
        {
            @Override
            public void onResult(ArrayList<Topic> topics)
            {
                addToTopicsAdapter.swap(topics);
            }

            @Override
            public void onFail(String failMessage)
            {
                Toast.makeText(getActivity(), failMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
