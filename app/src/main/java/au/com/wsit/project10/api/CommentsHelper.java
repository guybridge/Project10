package au.com.wsit.project10.api;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import au.com.wsit.project10.json.JsonHelper;
import au.com.wsit.project10.model.Comment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * Created by guyb on 28/01/17.
 */

public class CommentsHelper
{
    private static final String TAG = CommentsHelper.class.getSimpleName();
    ReplaySubject<ArrayList<Comment>> notifier = ReplaySubject.create();


    public void getComments(String videoId)
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
                    notifier.onNext(comments);
                }
                catch (IOException e)
                {
                    Log.i(TAG, "Error parsing comments: " + e.getMessage());
                    notifier.onError(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                notifier.onError(t);
            }
        });
    }

    public Observable<ArrayList<Comment>> asObservable()
    {
        return notifier;
    }
}
