package au.com.wsit.project10.api;


import au.com.wsit.project10.utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by guyb on 29/12/16.
 */

public interface YouTubeApiService
{
    String YOUTUBE_SEARCH_BASE_URL = "https://www.googleapis.com/youtube/v3/";
    // Example

    @GET("search?key=" + Constants.YOUTUBE_API_KEY + "&part=snippet&type=video&maxResults=50")
    Call<ResponseBody> results(@Query("q") String query);

}
