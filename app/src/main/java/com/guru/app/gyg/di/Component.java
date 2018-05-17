package com.guru.app.gyg.di;

import com.guru.app.gyg.GetYourGuideApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@dagger.Component(modules = {
        AndroidSupportInjectionModule.class,
        BuildersModule.class,
        SystemModule.class,
        ManagersModule.class,
        NetworkModule.class})
public interface Component {

    void inject(GetYourGuideApplication application);

    @dagger.Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(GetYourGuideApplication application);

        Component build();
    }
}
