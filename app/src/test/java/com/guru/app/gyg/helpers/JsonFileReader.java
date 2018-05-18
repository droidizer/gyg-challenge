package com.guru.app.gyg.helpers;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.Observable;

public class JsonFileReader {

    public static <T> Observable<T> read(Context context, String fileName, Gson gson, Class<T> convertTo) {
        return Observable.defer(() -> {
            try {
                return read(context.getAssets().open("mock".concat(File.separator).concat(fileName)), gson, convertTo);
            } catch (IOException e) {
                return Observable.error(e);
            }
        });
    }

    public static <T> Observable<T> read(InputStream inputStream, Gson gson, Class<T> convertTo) {
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
            return Observable.just(gson.fromJson(jsonReader, convertTo));
        } finally {
            closeStream(inputStream);
        }
    }


    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}