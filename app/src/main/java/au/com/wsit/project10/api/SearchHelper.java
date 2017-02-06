package au.com.wsit.project10.api;


import java.io.IOException;
import java.util.ArrayList;

import au.com.wsit.project10.json.JsonHelper;
import au.com.wsit.project10.model.Result;
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

public class SearchHelper
{
    private static final String TAG = SearchHelper.class.getSimpleName();
    ReplaySubject<ArrayList<Result>> notifier = ReplaySubject.create();

    public void search(String searchTerm)
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
                    notifier.onNext(results);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                notifier.onError(t);
            }
        });

    }

    public Observable<ArrayList<Result>> asObservable()
    {
        return notifier;
    }
}
