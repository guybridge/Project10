package au.com.wsit.project10.api;

import android.util.Log;

import com.squareup.picasso.Callback;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

import au.com.wsit.project10.model.Result;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Get the most popular videos on start
 */

public class PopularHelper
{
    public static final String TAG = PopularHelper.class.getSimpleName();

    public interface Callback
    {
        void onSuccess(ArrayList<Result> results);
        void onFail(String errorMessage);
    }

    public void get(final Callback callback)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YouTubeApiService.YOUTUBE_SEARCH_BASE_URL)
                .build();

        YouTubeApiService service = retrofit.create(YouTubeApiService.class);

        Call<ResponseBody> popularSearch = service.popular();
        popularSearch.enqueue(new retrofit2.Callback<ResponseBody>()
        {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try
                {
                    ArrayList<Result> results = JsonHelper.parseResults(response.body().string());
                    callback.onSuccess(results);
                }
                catch (IOException e)
                {
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
