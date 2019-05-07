package com.example.newsfeed.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.newsfeed.data.AppRepository;
import com.example.newsfeed.data.model.Article;


public class ArticleDetailsViewModel extends ViewModel {

    private static final String TAG = ArticleDetailsViewModel.class.getSimpleName();


    public ArticleDetailsViewModel() {
    }


    public LiveData<Article> findArticleByTitle(String title) {
        return AppRepository.getInstance().findArticleByTitle(title);
    }


    public void deleteArticleFromDatabase(Article article) {
        AppRepository.getInstance().deleteArticleFromDatabase(article);
    }


    public void insertArticleIntoDatabase(Article article) {
        AppRepository.getInstance().insertArticleIntoDatabase(article);
    }

}
