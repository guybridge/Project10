package au.com.wsit.project10.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by guyb on 29/12/16.
 */

// https://www.googleapis.com/youtube/v3/search?part=snippet&q=eminem&type=video&key=AIzaSyBiMZ2eKQ2yKpblyxCm41AjMyvbQhLA3ZI

public interface YouTubeApiService
{
    String YOUTUBE_SEARCH_BASE_URL = "https://www.googleapis.com/youtube/v3/search?";
    // Example

    @GET("key=AIzaSyBiMZ2eKQ2yKpblyxCm41AjMyvbQhLA3ZI&part=snippet&type=video&maxResults=50&q={q}")
    Call<ResponseBody> results(@Path("q") String q);

}
