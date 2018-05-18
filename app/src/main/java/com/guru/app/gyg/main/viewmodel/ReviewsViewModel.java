package com.guru.app.gyg.main.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.databinding.Bindable;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.guru.app.gyg.BR;
import com.guru.app.gyg.R;
import com.guru.app.gyg.misc.AndroidBaseViewModel;
import com.guru.app.gyg.model.Review;
import com.guru.app.gyg.model.ReviewModel;
import com.guru.app.gyg.network.IRepositoryManager;
import com.guru.app.gyg.utils.rv.AndroidItemBinder;
import com.guru.app.gyg.utils.rv.ItemClickListener;
import com.guru.app.gyg.utils.rv.PageDescriptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import retrofit2.HttpException;

public class ReviewsViewModel extends AndroidBaseViewModel {

    private final IRepositoryManager mRepositoryManager;

    private final Resources mResources;

    private Disposable mDisposable = Disposables.disposed();

    private boolean mLoading;

    private String mErrorMessage;

    private boolean mErrorVisible;

    private Map<Class<?>, AndroidItemBinder> mTemplates;

    private List<AndroidBaseViewModel> mListItems;
    private PageDescriptor mPageDescriptor;

    @Inject
    public ReviewsViewModel(Application application, IRepositoryManager repositoryManager, Resources resources) {
        super(application);
        mRepositoryManager = repositoryManager;
        mResources = resources;
    }

    @Override
    public void onStart() {
        super.onStart();
        setLoading(true);
        mListItems = new ArrayList<>();
        mPageDescriptor = new PageDescriptor.PageDescriptorBuilder()
                .setPageSize(20)
                .setStartPage(1)
                .build();
        mPageDescriptor.setCurrentPage(1);
        setNextPage();
    }

    public PageDescriptor getNextPage() {
        return mPageDescriptor;
    }

    public void setNextPage() {
        mDisposable.dispose();
        mDisposable = mRepositoryManager.getReviews(mPageDescriptor.getPageSize(), mPageDescriptor.getCurrentPage() - 1)
                .filter(response -> response != null && response.getReviews() != null)
                .subscribe(this::notifyResult,
                        this::notifyError);
    }

    public Map<Class<?>, AndroidItemBinder> getTemplatesForObjects() {
        if (mTemplates == null) {
            mTemplates = new HashMap<>();
            mTemplates.put(ReviewItemViewModel.class, new AndroidItemBinder(R.layout.rv_item, BR.viewModel));
        }
        return mTemplates;
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        int bottomMargin = (int) mResources.getDimension(R.dimen.margin_4);
        int horizontalMargin = (int) mResources.getDimension(R.dimen.activity_horizontal_margin);
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = bottomMargin;
                outRect.right = horizontalMargin;
                outRect.left = horizontalMargin;
            }
        };
    }

    public ItemClickListener getItemClickListener() {
        return ((view, item) -> {
        });
    }

    @Bindable
    public List<?> getListItems() {
        return mListItems;
    }

    @Bindable
    public String getErrorMessage() {
        return mErrorMessage;
    }

    @Bindable
    public boolean isErrorVisible() {
        return mErrorVisible;
    }

    private void notifyError(Throwable throwable) {
        setLoading(false);
        String errorMessage = (throwable instanceof HttpException || throwable instanceof IOException)
                ? mResources.getString(R.string.connection_error)
                : mResources.getString(R.string.error);
        setErrorMessage(errorMessage);
        setErrorVisible(true);
    }

    public void setErrorMessage(String message) {
        mErrorMessage = message;
        notifyPropertyChanged(BR.errorMessage);
    }

    private void setErrorVisible(boolean errorVisible) {
        mErrorVisible = mListItems != null && mListItems.isEmpty() && !isLoading() && errorVisible;
        notifyPropertyChanged(BR.errorVisible);
    }

    public void notifyBindings() {
        notifyPropertyChanged(BR.listItems);
    }

    private void setLoading(boolean loading) {
        mLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isLoading() {
        return mLoading;
    }

    @Override
    public void onDestroy() {
        mDisposable.dispose();
        mListItems.clear();
        super.onDestroy();
    }

    private void notifyResult(ReviewModel reviewModel) {
        setLoading(false);
        if (!reviewModel.getReviews().isEmpty()) {
            for (Review r : reviewModel.getReviews()) {
                if (!TextUtils.isEmpty(r.getTitle())) {
                    mListItems.add(new ReviewItemViewModel(getApplication(), r));
                }
            }
            notifyBindings();
            setNextPage();
        } else {
            setErrorMessage(mResources.getString(R.string.no_results));
            setErrorVisible(true);
        }
    }

    public static class Factory implements ViewModelProvider.Factory {

        @NonNull
        Application mApplication;
        private IRepositoryManager mApiManager;
        private Resources mResources;

        @Inject
        public Factory(@NonNull Application application, IRepositoryManager apiManager, Resources resources) {
            mApplication = application;
            mApiManager = apiManager;
            mResources = resources;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ReviewsViewModel(mApplication, mApiManager, mResources);
        }
    }
}
