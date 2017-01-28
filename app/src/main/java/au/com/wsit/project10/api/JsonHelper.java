package au.com.wsit.project10.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import au.com.wsit.project10.model.Comment;
import au.com.wsit.project10.model.Result;
import au.com.wsit.project10.utils.Constants;

/**
 * Created by guyb on 28/01/17.
 */

public class JsonHelper
{
    private static final String TAG = JsonHelper.class.getSimpleName();

    public static final ArrayList<Result> parseResults(String data)
    {
        try
        {
            JSONObject jsonData = new JSONObject(data);
            JSONArray resultsArray = jsonData.getJSONArray(Constants.ITEMS);
            ArrayList<Result> resultsArrayList = new ArrayList<>();

            for(int i = 0; i < resultsArray.length(); i++)
            {
                Result result = new Result();

                JSONObject resultInstance = resultsArray.getJSONObject(i);

                JSONObject id = resultInstance.getJSONObject(Constants.ID);

                String videoId = id.getString(Constants.JSON_VIDEO_ID);

                JSONObject snippet = resultInstance.getJSONObject(Constants.SNIPPET);
                String videoTitle = snippet.getString(Constants.JSON_VIDEO_TITLE);
                String videoDescription = snippet.getString(Constants.JSON_VIDEO_DESCRIPTION);

                JSONObject thumbnails = snippet.getJSONObject(Constants.THUMBNAILS);
                JSONObject largeThumbnail = thumbnails.getJSONObject(Constants.HIGH);
                String imageUrl  = largeThumbnail.getString(Constants.URL);

                result.setVideoTitle(videoTitle);
                result.setVideoDescription(videoDescription);
                result.setVideoID(videoId);
                result.setVideoUrl(imageUrl);

                resultsArrayList.add(result);
            }

            return resultsArrayList;
        }
        catch (JSONException | NullPointerException e)
        {
            Log.i(TAG, "Error parsing Json data: " + e.getMessage());
            return null;
        }
    }

    public static final ArrayList<Comment> parseComments(String data)
    {

            try
            {
                JSONObject jsonData = new JSONObject(data);
                JSONArray jsonArrayData = jsonData.getJSONArray(Constants.ITEMS);

                ArrayList<Comment> commentArrayList = new ArrayList<>();

                for (int i = 0; i < jsonArrayData.length(); i++)
                {

                    Comment comment = new Comment();

                    JSONObject commentInstance = jsonArrayData.getJSONObject(i);
                    JSONObject snippet = commentInstance.getJSONObject(Constants.SNIPPET);
                    JSONObject topLevelComment = snippet.getJSONObject(Constants.TOP_LEVEL_COMMENT);
                    JSONObject snippetInComment = topLevelComment.getJSONObject(Constants.SNIPPET);

                    String authorDisplayName = snippetInComment.getString(Constants.AUTHOR_DISPLAY_NAME);
                    String authorProfileImageUrl = snippetInComment.getString(Constants.AUTHOR_IMAGE);
                    String authorComment = snippetInComment.getString(Constants.TEXT_DISPLAY);

                    comment.setAuthorName(authorDisplayName);
                    comment.setAuthorImageUrl(authorProfileImageUrl);
                    comment.setAuthorComment(authorComment);

                    commentArrayList.add(comment);
                }

                return commentArrayList;

            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
    }
}
