package com.example.newsfeed.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.newsfeed.data.model.Article;

import java.util.List;

public class WidgetUpdateService extends IntentService {

    public static final String UPDATE_ARTICLES =
            "com.example.newsfeed.action.update_articles";

    private static List<Article> mArticleList;
    private static Context mContext;


    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (UPDATE_ARTICLES.equals(action)) {
                handleActionUpdateArticle();
            }
        }
    }


    private void handleActionUpdateArticle() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewsFeedWidget.class));
        NewsFeedWidget.updateAppWidget(this, appWidgetManager, appWidgetIds, mArticleList);
    }
}
