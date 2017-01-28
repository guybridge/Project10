package au.com.wsit.project10.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import au.com.wsit.project10.R;
import au.com.wsit.project10.adapters.TopicsAdapter;
import au.com.wsit.project10.api.TopicHelper;
import au.com.wsit.project10.model.Topic;

public class TopicsActivity extends AppCompatActivity
{
    private static final String TAG = TopicsActivity.class.getSimpleName();
    private EditText addTopicEditText;
    private TextView addTopicButton;
    private RecyclerView topicsRecyclerView;
    private TopicsAdapter topicsAdapter;
    private TopicHelper topicHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        topicHelper = new TopicHelper(TopicsActivity.this);

        addTopicEditText = (EditText) findViewById(R.id.topicEditText);
        addTopicButton = (TextView) findViewById(R.id.addButton);
        topicsRecyclerView = (RecyclerView) findViewById(R.id.topicsRecyclerView);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topicsAdapter = new TopicsAdapter(this);
        topicsRecyclerView.setAdapter(topicsAdapter);

        addTopicButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setScaleX(0);
                v.setScaleY(0);
                v.animate().scaleX(1).scaleY(1).start();
                // TODO: Add item to topics database
                String topicName = addTopicEditText.getText().toString();
                topicHelper.createTopic(topicName);

                getTopics();
            }
        });

        getTopics();
    }

    private void getTopics()
    {

        topicsAdapter.swap(topicHelper.getTopics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_topics, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
