package com.guru.app.gyg.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.guru.app.gyg.R
import com.guru.app.gyg.main.viewmodel.ReviewsViewModel
import com.guru.app.gyg.misc.AndroidBaseInjectableActivity
import javax.inject.Inject

class MainActivity : AndroidBaseInjectableActivity() {

    @Inject
    lateinit var mFactory: ReviewsViewModel.Factory

    @Inject
    lateinit var mViewModel: ReviewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mFactory).get(ReviewsViewModel::class.java)
        setContentView(R.layout.activity_main, BR.reviewViewModel, mViewModel)
    }
}
