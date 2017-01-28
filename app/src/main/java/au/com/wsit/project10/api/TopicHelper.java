package au.com.wsit.project10.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import au.com.wsit.project10.model.Result;
import au.com.wsit.project10.model.Topic;
import au.com.wsit.project10.utils.Constants;

/**
 * Created by guyb on 28/01/17.
 */

public class TopicHelper
{
    private static final String TAG = TopicHelper.class.getSimpleName();
    private Context context;

    public interface GetCallback
    {
        void onResult(ArrayList<Topic> topics);
        void onFail(String failMessage);
    }

    public interface SaveCallback
    {
        void onSuccess();
        void onFail(String errorMessage);
    }

    public TopicHelper(Context context)
    {
        this.context = context;
    }

    public void getTopics(final GetCallback callback)
    {

        final ArrayList<Topic> topics = new ArrayList<>();

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery(Constants.TOPICS_CLASS);
        parseQuery.findInBackground(new FindCallback<ParseObject>()
        {

            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if(e == null)
                {
                    for (ParseObject object : objects)
                    {
                        Topic topic = new Topic();
                        ArrayList<Result> videos = new ArrayList<>();

                        String topicName = object.getString(Constants.TOPIC_NAME);
                        String topicID = object.getObjectId();
                        String videosList = (String) object.get(Constants.VIDEO_ID);

                        Log.i(TAG, "VideosList is: " + videosList);

                        topic.setTopicName(topicName);
                        topic.setTopicId(topicID);

                        topics.add(topic);

                    }

                    callback.onResult(topics);
                }
                else
                {
                    callback.onFail(e.getMessage());
                }

            }
        });
    }

    public void createTopic(String topicName, final SaveCallback callback)
    {
        ParseObject parseObject = new ParseObject(Constants.TOPICS_CLASS);
        parseObject.put(Constants.TOPIC_NAME, topicName);
        parseObject.saveInBackground(new com.parse.SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e == null)
                {
                    callback.onSuccess();
                }
                else
                {
                    callback.onFail(e.getMessage());
                }
            }
        });
    }

    // Adds a video to a topic
   public void addVideoToTopic(String videoID, String topicId, final SaveCallback callback)
   {
        ParseQuery<ParseObject> topicQuery = ParseQuery.getQuery(Constants.TOPICS_CLASS);
       try
       {
           ParseObject topic = topicQuery.get(topicId);
           // Add the videoID to the topic
           topic.add(Constants.VIDEO_ID, videoID);
           topic.saveInBackground(new com.parse.SaveCallback()
           {
               @Override
               public void done(ParseException e)
               {
                   if(e == null)
                   {
                       callback.onSuccess();
                   }
                   else
                   {
                       callback.onFail(e.getMessage());
                   }

               }
           });
       }
       catch (ParseException e)
       {
           e.printStackTrace();
           callback.onFail(e.getMessage());
       }

   }

}
