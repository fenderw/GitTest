package com.test.gittest.retrofit;

import com.test.gittest.utils.Connectivity;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by F on 21/02/17.
 */

public class App extends com.activeandroid.app.Application {

    private static GitApi gitApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();


        //setup cache
        File httpCacheDirectory = new File(getApplicationContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        if (Connectivity.isConnected(getApplicationContext())) {
                            // read from cache for 5 minute
                            int maxAge = 60 * 5;
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + maxAge)
                                    .build();
                        } else {
                            // tolerate a weeks stale
                            int maxStale = 60 * 60 * 24 * 7;
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                    }
                })
                .cache(cache)
                .build();
        //
        retrofit = new Retrofit.Builder()
                .baseUrl(GitApi.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitApi = retrofit.create(GitApi.class);
    }

    public static GitApi getApi() {
        return gitApi;
    }
}
