package com.guru.app.gyg

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.guru.app.gyg.helpers.CustomSchedulersTestRule
import org.junit.Before
import org.junit.ClassRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
open class BaseTest {

    @Mock
    lateinit var mApplication: Application

    companion object {
        @ClassRule
        @JvmField
        val schedulers = CustomSchedulersTestRule()

        @ClassRule
        @JvmField
        val instantRule = InstantTaskExecutorRule()
    }

    @Before
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
}