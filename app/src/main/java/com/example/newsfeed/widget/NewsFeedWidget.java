package com.example.newsfeed.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.newsfeed.R;
import com.example.newsfeed.data.model.Article;
import com.example.newsfeed.ui.activities.MainActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NewsFeedWidget extends AppWidgetProvider {

    private static List<Article> mArticleList;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);


    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetId,
                                       List<Article> articles) {

        mArticleList = articles;
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_feed_widget);

        views.setTextViewText(R.id.widget_articles, context.getString(R.string.widget_text));

        views.setOnClickPendingIntent(R.id.article_linear_layout, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, new int[]{appWidgetId}, mArticleList);
        }
    }

}

