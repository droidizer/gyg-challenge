package com.guru.app.gyg;

import android.app.Activity;
import android.app.Application;

import com.guru.app.gyg.di.DaggerComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class GetYourGuideApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerComponent.builder().application(this).build().inject(this);
    }

    @Override
    public  AndroidInjector<Activity> activityInjector() {
        return mAndroidActivityInjector;
    }

}
