package com.guru.app.gyg.utils;

import android.arch.lifecycle.LifecycleObserver;

import com.guru.app.gyg.misc.ClickItemWrapper;
import com.guru.app.gyg.misc.SingleLiveEvent;

public interface INeedClickListener extends LifecycleObserver {

    SingleLiveEvent<ClickItemWrapper> getItemClickListenerNotifier();
}