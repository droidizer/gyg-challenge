package com.guru.app.gyg

import android.content.res.Resources
import com.google.gson.Gson
import com.guru.app.gyg.helpers.JsonFileReader
import com.guru.app.gyg.main.viewmodel.ReviewItemViewModel
import com.guru.app.gyg.main.viewmodel.ReviewsViewModel
import com.guru.app.gyg.model.ReviewModel
import com.guru.app.gyg.network.RepositoryManager
import io.reactivex.Observable
import org.fest.assertions.api.Assertions
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito


class ReviewsViewModelTest : BaseTest() {

    @Mock
    lateinit var mApiManager: RepositoryManager

    @Mock
    lateinit var mResources: Resources

    private val mWeatherModel: ReviewsViewModel by lazy {
        ReviewsViewModel(mApplication, mApiManager, mResources)
    }

    @Test
    fun test_weather_data_is_valid() {
        Mockito.`when`(mApiManager.getReviews(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(getReviews())

        mWeatherModel.onStart()
        Assertions.assertThat(mWeatherModel.listItems).isNotNull
        Assertions.assertThat(mWeatherModel.listItems.size).isNotZero
        Assertions.assertThat(mWeatherModel.listItems.size).isEqualTo(10)
        Assertions.assertThat(mWeatherModel.listItems.get(0)).isInstanceOf(ReviewItemViewModel::class.java)
        Assertions.assertThat(mWeatherModel.isErrorVisible).isFalse
    }

    @Test
    fun test_weather_data_error() {
        Mockito.`when`(mApiManager.getReviews(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Observable.error(Throwable()))

        mWeatherModel.onStart()
        Assertions.assertThat(mWeatherModel.isErrorVisible)
        Assertions.assertThat(mWeatherModel.errorMessage).isEqualToIgnoringCase(mResources.getString(R.string.connection_error))
        Assertions.assertThat(mWeatherModel.isLoading).isFalse
        Assertions.assertThat(mWeatherModel.listItems.size).isZero
    }

    private fun getReviews(): Observable<ReviewModel> =
            JsonFileReader.read(javaClass.classLoader.getResourceAsStream("reviews.json"), Gson(), ReviewModel::class.java)

}
