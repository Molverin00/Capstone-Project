package com.example.newsfeed.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsfeed.viewModel.ArticleDetailsViewModel;
import com.example.newsfeed.R;
import com.example.newsfeed.data.model.Article;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailsActivity extends AppCompatActivity {

    private static final String TAG = ArticleDetailsActivity.class.getSimpleName();

    private ArticleDetailsViewModel articleDetailsViewModel;

    private boolean bookmarked;

    @BindView(R.id.iv_image) ImageView articleImageView;
    @BindView(R.id.tv_title) TextView titleTextView;
    @BindView(R.id.tv_author) TextView authorTextView;
    @BindView(R.id.tv_source) TextView sourceTextView;
//    @BindView(R.id.tv_description) TextView descriptionTextView;
    @BindView(R.id.tv_content) TextView contentTextView;
    @BindView(R.id.tv_publish_date) TextView publishDateTextView;
    @BindView(R.id.floatingActionButton) FloatingActionButton fab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        ButterKnife.bind(this);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        articleDetailsViewModel = ViewModelProviders.of(this).get(ArticleDetailsViewModel.class);


        Intent intent = getIntent();

        if (intent.hasExtra("ArticleObject")) {

            Article article = intent.getParcelableExtra("ArticleObject");
            Log.d(TAG, article.toString());

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(this)
                    .load(article.getUrlToImage())
                    .apply(requestOptions)
                    .into(articleImageView);


            titleTextView.setText(article.getTitle());
            authorTextView.setText(article.getAuthor());
            sourceTextView.setText(article.getSource().getName());
            publishDateTextView.setText(article.getPublishedAt());
//            descriptionTextView.setText(article.getDescription());
            contentTextView.setText(article.getContent());

            articleDetailsViewModel.findArticleByTitle(article.getTitle()).observe(this, article1 -> {

                if (article1 != null) {

                    Log.d(TAG, "findArticleLiveDataByTitle: this article exists in database");
                    fab.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    bookmarked = true;
                } else {
                    Log.d(TAG, "findArticleLiveDataByTitle: this article does not exists in database");
                    fab.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    bookmarked = false;
                }

            });


            fab.setOnClickListener(v -> {

                if (bookmarked) {

                    Log.d(TAG, "onClick: delete from database");
                    articleDetailsViewModel.deleteArticleFromDatabase(article);

                } else {

                    Log.d(TAG, "onClick: insert into database");
                    articleDetailsViewModel.insertArticleIntoDatabase(article);

                }

            });

        }

    }
}
