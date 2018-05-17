package com.guru.app.gyg.misc;

import android.arch.lifecycle.LifecycleObserver;

public interface INeedClickListener extends LifecycleObserver {

    SingleLiveEvent<ClickItemWrapper> getItemClickListenerNotifier();
}