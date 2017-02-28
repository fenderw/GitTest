package com.test.gittest.retrofit;

/**
 * Created by F on 21/02/17.
 */

import com.test.gittest.models.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitApi {

    String ENDPOINT = "https://api.github.com";

    @GET("users/{user}/repos")
    Call<List<Repo>> getData(@Path("user") String user, @Query("type") String type);
}
