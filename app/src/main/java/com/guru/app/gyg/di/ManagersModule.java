package com.guru.app.gyg.di;

import com.guru.app.gyg.network.RepositoryManager;
import com.guru.app.gyg.network.IRepositoryManager;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ManagersModule {

    @Binds
    public abstract IRepositoryManager provideApiManager(RepositoryManager apiManager);
}
