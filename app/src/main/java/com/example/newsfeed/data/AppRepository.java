package com.example.newsfeed.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.newsfeed.BuildConfig;
import com.example.newsfeed.util.AppExecutors;
import com.example.newsfeed.util.MyApp;
import com.example.newsfeed.data.database.AppDatabase;
import com.example.newsfeed.data.model.ApiResponse;
import com.example.newsfeed.data.model.Article;
import com.example.newsfeed.data.network.ApiClient;
import com.example.newsfeed.data.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();

    private Context context = MyApp.getAppContext();


    private ApiService apiService = ApiClient.getClient().create(ApiService.class);
    private MutableLiveData<List<Article>> articleListLiveData = new MutableLiveData<>();


    private static final AppRepository ourInstance = new AppRepository();

    public static AppRepository getInstance() {
        return ourInstance;
    }

    private AppRepository() {

        loadArticlesFromApi();

    }


    public MutableLiveData<List<Article>> getArticleListLiveData() {
        return articleListLiveData;
    }

    public void setArticleListLiveData(List<Article> articleList) {
        this.articleListLiveData.setValue(articleList);
    }


    public void loadArticlesFromApi() {
        Call<ApiResponse> apiResponseCall = apiService.getResponse("us", BuildConfig.mApiKey);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (!response.isSuccessful()) {

                    Log.e(TAG, "onResponse: " + response.code() + " - " + response.message());
                    return;
                }

                if (response.body() == null) {

                    Log.e(TAG, "onResponse: response is empty");

                } else {

                    Log.d(TAG, "onResponse: " + response.body().getStatus());
                    Log.d(TAG, "onResponse: " + response.body().getTotalResults());
                    //Log.d(TAG, "onResponse: " + response.body().getArticles().toString());

                    setArticleListLiveData(response.body().getArticles());

                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });
    }


    public LiveData<Article> findArticleByTitle(String title) {
        return AppDatabase.getInstance(context).bookmarkedArticleDao().findArticleLiveDataByTitle(title);
    }


    public void deleteArticleFromDatabase(Article article) {

        String articleTitle = article.getTitle();

        AppExecutors.getInstance().diskIO().execute(() ->
                AppDatabase
                        .getInstance(context)
                        .bookmarkedArticleDao()
                        .deleteArticle(
                                AppDatabase
                                        .getInstance(context)
                                        .bookmarkedArticleDao()
                                        .findArticleByTitle(articleTitle)
                )
        );
    }


    public void insertArticleIntoDatabase(Article article) {
        AppExecutors.getInstance().diskIO().execute(() ->
                AppDatabase.getInstance(context).bookmarkedArticleDao().insertArticle(article));
    }

}
