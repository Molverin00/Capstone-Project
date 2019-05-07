package com.example.newsfeed.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.newsfeed.data.AppRepository;
import com.example.newsfeed.data.model.Article;

import java.util.List;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private MutableLiveData<List<Article>> articleListLiveData;
    private MutableLiveData<Article> selectedArticleLiveData;
    private MutableLiveData<Boolean> showArticleDetailsLiveData;
    private MutableLiveData<Integer> titleLiveData;


    public MainViewModel() {

        articleListLiveData = new MutableLiveData<>();
        selectedArticleLiveData = new MutableLiveData<>();
        showArticleDetailsLiveData = new MutableLiveData<>();
        titleLiveData = new MutableLiveData<>();

        setArticleListLiveData();

    }


    public MutableLiveData<Integer> getTitleLiveData() {
        return titleLiveData;
    }

    public void setTitleLiveData(int title) {
        this.titleLiveData.setValue(title);
    }


    public MutableLiveData<Article> getSelectedArticleLiveData() {
        return selectedArticleLiveData;
    }

    public void setSelectedArticleLiveData(Article selectedArticle) {
        this.selectedArticleLiveData.setValue(selectedArticle);
    }


    public MutableLiveData<Boolean> getShowArticleDetailsLiveData() {
        return showArticleDetailsLiveData;
    }

    public void setShowArticleDetailsLiveData(boolean showArticleDetails) {
        this.showArticleDetailsLiveData.setValue(showArticleDetails);
    }


    public MutableLiveData<List<Article>> getArticleListLiveData() {
        return articleListLiveData;
    }

    public void setArticleListLiveData() {
        this.articleListLiveData = AppRepository.getInstance().getArticleListLiveData();
    }


    public void loadArticlesFromApi() {
        AppRepository.getInstance().loadArticlesFromApi();
    }

}
