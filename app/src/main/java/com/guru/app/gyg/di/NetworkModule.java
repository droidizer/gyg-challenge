package com.guru.app.gyg.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.app.gyg.BuildConfig;
import com.guru.app.gyg.network.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class NetworkModule {

    private static final int CONNECTION_TIMEOUT_SECS = 20;


    private static final String BASE_URL = "https://www.getyourguide.com/";

    @Singleton
    @Provides
    public Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverterLibrary(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public RxJava2CallAdapterFactory providesRxJava2CallAdapter() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);
        }

        okHttpClientBuilder
                .connectTimeout(CONNECTION_TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT_SECS, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT_SECS, TimeUnit.SECONDS);

        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    public ApiService provideGygApiService(GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory);
        Retrofit retrofit = builder.client(okHttpClient).build();
        return retrofit.create(ApiService.class);
    }
}