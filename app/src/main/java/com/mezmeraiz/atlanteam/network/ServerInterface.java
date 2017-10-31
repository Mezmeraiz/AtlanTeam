package com.mezmeraiz.atlanteam.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by max on 31.10.17.
 */

public interface ServerInterface {

    @GET("/posts/{id}")
    Observable<ResponseBody> posts(@Path("id") int id);

    @GET("/comments/{id}")
    Observable<ResponseBody> comments(@Path("id") int id);

    @GET("/users")
    Observable<ResponseBody> users();

    @GET("/photos/{id}")
    Observable<ResponseBody> photos(@Path("id") int id);

}
