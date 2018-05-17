package com.guru.app.gyg.di;

import com.guru.app.gyg.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
