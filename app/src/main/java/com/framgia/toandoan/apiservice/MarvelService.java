package com.framgia.toandoan.apiservice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by framgia on 10/03/2017.
 */
public interface MarvelService {
    @GET("v1/public/characters")
    Call<ResponseBody> getCharacters(@Query("ts") long timeStap,
                                     @Query("apikey") String apiKey,
                                     @Query("hash") String hash);
}
