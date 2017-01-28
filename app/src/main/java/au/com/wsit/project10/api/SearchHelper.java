package au.com.wsit.project10.api;

import android.support.v7.widget.SearchView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import au.com.wsit.project10.model.Result;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by guyb on 28/01/17.
 */

public class SearchHelper
{
    private static final String TAG = SearchHelper.class.getSimpleName();

    public interface SearchCallback
    {
        void onSuccess(ArrayList<Result> results);
        void onFail(String errorMessage);
    }

    public void search(String searchTerm, final SearchCallback callback)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YouTubeApiService.YOUTUBE_SEARCH_BASE_URL)
                .build();

        YouTubeApiService service = retrofit.create(YouTubeApiService.class);
        Call<ResponseBody> searchCall = service.results(searchTerm);
        searchCall.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                // Parse Json Data
                try
                {
                    ArrayList<Result> results = JsonHelper.parseResults(response.body().string());
                    callback.onSuccess(results);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
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
