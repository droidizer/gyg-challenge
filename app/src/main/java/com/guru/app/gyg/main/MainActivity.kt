package com.guru.app.gyg.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.guru.app.gyg.R
import com.guru.app.gyg.main.viewmodel.ReviewViewModel
import com.guru.app.gyg.main.viewmodel.ReviewsViewModel
import com.guru.app.gyg.misc.AndroidBaseInjectableActivity
import javax.inject.Inject

class MainActivity : AndroidBaseInjectableActivity() {

    @Inject
    lateinit var mFactory: ReviewViewModel.Factory

    @Inject
    lateinit var mViewModel: ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mFactory).get(ReviewViewModel::class.java)
        setContentView(R.layout.activity_main, BR.reviewViewModel, mViewModel)
    }
}
