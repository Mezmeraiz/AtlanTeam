package com.mezmeraiz.atlanteam.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by max on 31.10.17.
 */

public class Request {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public Observable<ResponseBody> posts(int id) {
        return getInterface().posts(id);
    }

    public Observable<ResponseBody> comments(int id) {
        return getInterface().comments(id);
    }

    public Observable<ResponseBody> users() {
        return getInterface().users();
    }

    public Observable<ResponseBody> photos(int id) {
        return getInterface().photos(id);
    }

    private ServerInterface getInterface(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ServerInterface.class);
    }

    /*
    .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result ->
                        {

                        },
                        error ->
                        {

                        }
                );
            */

}
