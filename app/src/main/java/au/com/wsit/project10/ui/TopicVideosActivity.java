package au.com.wsit.project10.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.adapters.ResultsAdapter;
import au.com.wsit.project10.adapters.TopicsAdapter;
import au.com.wsit.project10.adapters.VideosAdapter;
import au.com.wsit.project10.api.TopicHelper;
import au.com.wsit.project10.model.Result;
import au.com.wsit.project10.model.Topic;
import au.com.wsit.project10.utils.Constants;
import rx.subjects.ReplaySubject;

public class TopicVideosActivity extends AppCompatActivity
{
    private static final String TAG = TopicVideosActivity.class.getSimpleName();
    private RecyclerView resultsRecycler;
    private VideosAdapter videosAdapter;
    private String topicID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_videos);

        resultsRecycler = (RecyclerView) findViewById(R.id.resultsRecyclerView);
        resultsRecycler.setLayoutManager(new LinearLayoutManager(this));
        videosAdapter = new VideosAdapter(this);
        resultsRecycler.setAdapter(videosAdapter);

        Intent intent = getIntent();
        String topicName = intent.getStringExtra(Constants.TOPIC_NAME);
        topicID = intent.getStringExtra(Constants.TOPIC_ID);
        getSupportActionBar().setTitle(topicName);

        getVideos();
    }

    private void getVideos()
    {
        TopicHelper topicHelper = new TopicHelper(this);
        topicHelper.getTopicVideosByTopicId(topicID);
        try
        {
            topicHelper.asVideoObersable().subscribe(videosAdapter);

        }
        catch (NullPointerException e)
        {
            Log.i(TAG, "Error observing " + e.getMessage());
        }

    }
}
