package com.example.newsfeed.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.newsfeed.data.model.Article;

import java.util.List;

@Dao
public interface BookmarkedArticleDao {

    @Query("SELECT * FROM articles_table")
    LiveData<List<Article>> getAllArticles();

    @Query("SELECT * FROM articles_table WHERE title LIKE :title")
    LiveData<Article> findArticleLiveDataByTitle(String title);

    @Query("SELECT * FROM articles_table WHERE title LIKE :title")
    Article findArticleByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Article article);

    @Delete
    void deleteArticle(Article article);

}
