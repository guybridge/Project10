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
import java.util.Objects;
import java.util.StringTokenizer;

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

    public interface GetVideosCallback
    {
        void onResult(ArrayList<Result> videos);
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

    public void getTopicVideosByTopicId(String topicId, GetVideosCallback callback)
    {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.TOPICS_CLASS);
        try
        {
            ParseObject topic = query.get(topicId);

            ArrayList<Result> videos = new ArrayList<>();
            // For each topic there is an array list of associated video data
            ArrayList<String> videosIDs = (ArrayList<String>) topic.get(Constants.VIDEO_ID);
            ArrayList<String> videosNames = (ArrayList<String>) topic.get(Constants.VIDEO_TITLE);
            ArrayList<String> videosDescriptions = (ArrayList<String>) topic.get(Constants.VIDEO_DESCRIPTION);
            ArrayList<String> videosImageUrls = (ArrayList<String>) topic.get(Constants.VIDEO_IMAGE_URL);

            // Loop through the IDs and get the data and store
            for(int i = 0; i < videosIDs.size(); i++)
            {
                Result video = new Result();
                video.setVideoID(videosIDs.get(i));
                video.setVideoDescription(videosDescriptions.get(i));
                video.setVideoTitle(videosNames.get(i));
                video.setVideoUrl(videosImageUrls.get(i));
                Log.i(TAG, video.getVideoTitle() + " " + video.getVideoDescription() + " " + video.getVideoID());
                videos.add(video);
            }

            callback.onResult(videos);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
            callback.onFail(e.getMessage());
        }
    }

    // Get a list of topics
    public void getTopics(final GetCallback callback)
    {

        final ArrayList<Topic> topics = new ArrayList<>();

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery(Constants.TOPICS_CLASS);
        parseQuery.orderByDescending("createdAt");
        parseQuery.findInBackground(new FindCallback<ParseObject>()
        {

            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if(e == null)
                {
                    int count = 0;
                    for (ParseObject object : objects)
                    {
                        Topic topic = new Topic();
                        ArrayList<Result> videos = new ArrayList<>();

                        // Get the topic name and ID
                        String topicName = object.getString(Constants.TOPIC_NAME);
                        String topicID = object.getObjectId();

                        // For each topic there is an array list of associated video data
                        ArrayList<String> videosIDs = (ArrayList<String>) object.get(Constants.VIDEO_ID);
                        ArrayList<String> videosNames = (ArrayList<String>) object.get(Constants.VIDEO_ID);
                        ArrayList<String> videosDescriptions = (ArrayList<String>) object.get(Constants.VIDEO_ID);

                        try
                        {
                            topic.setVideoCount(videosIDs.size());

                            // Loop through the IDs and get the data and store
                            for(int i = 0; i < videosIDs.size(); i++)
                            {
                                Result video = new Result();
                                video.setVideoID(videosIDs.get(i));
                                video.setVideoDescription(videosDescriptions.get(i));
                                video.setVideoTitle(videosNames.get(i));
                                videos.add(video);
                            }
                        }
                        catch(NullPointerException error)
                        {
                            Log.i(TAG, "Error " + error.getMessage());
                        }

                        topic.setTopicVideosList(videos);
                        topic.setTopicName(topicName);
                        topic.setTopicId(topicID);

                        topics.add(topic);
                        count++;

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

    // Create a new topic
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
   public void addVideoToTopic(String videoImageUrl, String videoName, String videoDescription, String videoID, String topicId, final SaveCallback callback)
   {
        ParseQuery<ParseObject> topicQuery = ParseQuery.getQuery(Constants.TOPICS_CLASS);
       try
       {
           ParseObject topic = topicQuery.get(topicId);
           // Add the videoID to the topic

           topic.add(Constants.VIDEO_ID, videoID);
           topic.add(Constants.VIDEO_TITLE, videoName);
           topic.add(Constants.VIDEO_DESCRIPTION, videoDescription);
           topic.add(Constants.VIDEO_IMAGE_URL, videoImageUrl);

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
