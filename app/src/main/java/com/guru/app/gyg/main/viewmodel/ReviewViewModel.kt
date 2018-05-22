package com.guru.app.gyg.main.viewmodel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.res.Resources
import android.databinding.Bindable
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.blackbelt.bindings.recyclerviewbindings.AndroidItemBinder
import com.blackbelt.bindings.recyclerviewbindings.PageDescriptor
import com.guru.app.gyg.BR
import com.guru.app.gyg.R
import com.guru.app.gyg.misc.AndroidBaseViewModel
import com.guru.app.gyg.model.ReviewModel
import com.guru.app.gyg.network.IRepositoryManager
import io.reactivex.disposables.Disposables
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReviewViewModel @Inject constructor(application: Application, repositoryManager: IRepositoryManager, resources: Resources) : AndroidBaseViewModel(application) {

    private val mRepositoryManager = repositoryManager

    private val mResources = resources

    private var mDisposable = Disposables.disposed()

    private var mLoading: Boolean = false

    private var mErrorMessage = ""

    private var mErrorVisible: Boolean = false

    private var mTemplates: MutableMap<Class<*>, AndroidItemBinder> = hashMapOf(ReviewItemViewModel::class.java to AndroidItemBinder(R.layout.rv_item, BR.viewModel))

    private var mPageDescriptor = PageDescriptor.setPageSize(20).setStartPage(1).setThreshold(5).build()

    fun getNextPage(): PageDescriptor {
        return mPageDescriptor
    }

    fun getTemplatesForObjects(): Map<Class<*>, AndroidItemBinder> = mTemplates

    private val mListItems = ArrayList<Any>()

    fun getRepositories(): List<Any> = mListItems

    fun getItemDecoration(): RecyclerView.ItemDecoration {
        val bottomMargin = mResources.getDimension(R.dimen.margin_4).toInt()
        val horizontalMargin = mResources.getDimension(R.dimen.activity_horizontal_margin).toInt()
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                outRect.top = bottomMargin
                outRect.right = horizontalMargin
                outRect.left = horizontalMargin
            }
        }
    }

    @Bindable
    fun isLoading(): Boolean {
        return mLoading
    }

    @Bindable
    fun getListItems(): List<*> {
        return mListItems
    }

    @Bindable
    fun getErrorMessage(): String {
        return mErrorMessage
    }

    @Bindable
    fun isErrorVisible(): Boolean {
        return mErrorVisible
    }

    private fun notifyError(throwable: Throwable) {
        setLoading(false)
        val errorMessage = if (throwable is HttpException || throwable is IOException)
            mResources.getString(R.string.connection_error)
        else
            mResources.getString(R.string.error)
        setErrorMessage(errorMessage)
        setErrorVisible(true)
    }

    fun setErrorMessage(message: String) {
        mErrorMessage = message
        notifyPropertyChanged(BR.errorMessage)
    }

    private fun setErrorVisible(errorVisible: Boolean) {
        mErrorVisible = mListItems.isEmpty() && !isLoading() && errorVisible
        notifyPropertyChanged(BR.errorVisible)
    }

    private fun notifyBindings() {
        notifyPropertyChanged(BR.listItems)
    }

    private fun setLoading(loading: Boolean) {
        mLoading = loading
        notifyPropertyChanged(BR.loading)
    }

    override fun onDestroy() {
        mDisposable.dispose()
        super.onDestroy()
    }

    private fun notifyResult(reviewModel: ReviewModel) {
        setLoading(false)
        if (!reviewModel.reviews.isEmpty()) {
            for (r in reviewModel.reviews) {
                if (!TextUtils.isEmpty(r.title)) {
                    mListItems.add(ReviewItemViewModel(getApplication(), r))
                }
            }
            notifyBindings()
            setNextPage(mPageDescriptor)
            setErrorVisible(false)
        } else {
            setErrorMessage(mResources.getString(R.string.no_results))
            setErrorVisible(true)
        }
    }

    fun setNextPage(pageDescriptor: PageDescriptor) {
        mDisposable.dispose()
        mDisposable = mRepositoryManager.getReviews(pageDescriptor.getPageSize(), pageDescriptor.getCurrentPage() - 1)
                .filter { response -> response.reviews != null }
                .subscribe({ it -> this.notifyResult(it) },
                        { throwable -> this.notifyError(throwable) })
    }

    class Factory @Inject
    constructor(internal var mApplication: Application, private val mApiManager: IRepositoryManager, private val mResources: Resources) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReviewViewModel(mApplication, mApiManager, mResources) as T
        }
    }
}