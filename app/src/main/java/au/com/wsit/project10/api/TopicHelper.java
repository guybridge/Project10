package au.com.wsit.project10.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import au.com.wsit.project10.model.Topic;
import au.com.wsit.project10.utils.Constants;

/**
 * Created by guyb on 28/01/17.
 */

public class TopicHelper
{
    private static final String TAG = TopicHelper.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public TopicHelper(Context context)
    {
        this.sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();

    }

    public ArrayList<Topic> getTopics()
    {
        String jsonTopics = sharedPreferences.getString(Constants.TOPICS_CLASS, "");
        Gson gson = new Gson();

        ArrayList<Topic> topicArrayList = gson.fromJson(jsonTopics, new TypeToken<List<Topic>>(){

        }.getType());

        if(topicArrayList != null)
        {
            return topicArrayList;
        }
        else
        {
            ArrayList<Topic> topicArrayListEmpty = new ArrayList<>();
            return  topicArrayListEmpty;
        }

    }

    public void createTopic(String topicName)
    {
        // Get the topics first
        ArrayList<Topic> topicArrayList = getTopics();

        Topic topic = new Topic();
        topic.setTopicName(topicName);
        topicArrayList.add(topic);

        Gson gson = new Gson();
        String json = gson.toJson(topicArrayList);

        editor.putString(Constants.TOPICS_CLASS, json);
        editor.apply();
    }

    // Adds a video to a topic
   public void addVideo()
   {

   }

}
