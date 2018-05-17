package com.guru.app.gyg.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.guru.app.gyg.GetYourGuideApplication;

import dagger.Module;
import dagger.Provides;

@Module
class SystemModule {

    @Provides
    public Context providesContext(GetYourGuideApplication application) {
        return application;
    }

    @Provides
    public Resources providesResources(Context context) {
        return context.getResources();
    }

    @Provides
    public Application providesApplication(GetYourGuideApplication application) {
        return application;
    }
}
