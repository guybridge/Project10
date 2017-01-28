package au.com.wsit.project10.api;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import au.com.wsit.project10.model.Comment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by guyb on 28/01/17.
 */

public class CommentsHelper
{
    private static final String TAG = CommentsHelper.class.getSimpleName();

    public interface CommentsCallback
    {
        void onSuccess(ArrayList<Comment> comments);
        void onFail(String failMessage);
    }

    public void getComments(String videoId, final CommentsCallback callback)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YouTubeApiService.YOUTUBE_SEARCH_BASE_URL)
                .build();

        YouTubeApiService service = retrofit.create(YouTubeApiService.class);
        Call<ResponseBody> commentsQuery = service.comments(videoId);
        commentsQuery.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try
                {
                    ArrayList<Comment> comments = JsonHelper.parseComments(response.body().string());
                    callback.onSuccess(comments);
                }
                catch (IOException e)
                {
                    Log.i(TAG, "Error parsing comments: " + e.getMessage());
                    callback.onFail(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                callback.onFail(t.getMessage());
            }
        });
    }
}
