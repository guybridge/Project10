package au.com.wsit.project10.api;

import java.util.ArrayList;

import au.com.wsit.project10.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by guyb on 29/12/16.
 */

public interface CapitolWordsService
{
    String CAPITOL_WORDS_BASE_URL = "http://capitolwords.org/api/1";
    String API_KEY = "";

    @GET("dates.json?phrase={keyword}")
    Call<ArrayList<Result>> results(@Path("keyword") String keyword);


}
