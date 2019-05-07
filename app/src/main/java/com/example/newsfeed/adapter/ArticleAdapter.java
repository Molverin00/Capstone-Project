package com.example.newsfeed.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsfeed.R;
import com.example.newsfeed.data.model.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private static final String TAG = ArticleAdapter.class.getSimpleName();


    private Context mContext;
    private OnArticleListener onArticleListener;
    private List<Article> articleList;


    public ArticleAdapter(Context mContext, List<Article> articleList, OnArticleListener onArticleListener) {
        this.mContext = mContext;
        this.articleList = articleList;
        this.onArticleListener = onArticleListener;
    }

    @NonNull
    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.articles_list_item, viewGroup, false);

        return new ArticleViewHolder(view, onArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ArticleViewHolder articleViewHolder, int i) {

        Article article = articleList.get(i);

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(mContext)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .into(articleViewHolder.articleImageView);
        articleViewHolder.titleTextView.setText(article.getTitle());
        articleViewHolder.authorTextView.setText(article.getAuthor());
        articleViewHolder.sourceTextView.setText(article.getSource().getName());
        articleViewHolder.publishDateTextView.setText(article.getPublishedAt());

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnArticleListener onArticleListener;

        CardView articleCardView;
        ImageView articleImageView;
        TextView titleTextView;
        TextView authorTextView;
        TextView sourceTextView;
        TextView publishDateTextView;

        public ArticleViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
            super(itemView);

            articleCardView = itemView.findViewById(R.id.cv_article);
            articleImageView = itemView.findViewById(R.id.iv_article_image);
            titleTextView = itemView.findViewById(R.id.tv_article_title);
            authorTextView = itemView.findViewById(R.id.tv_article_author);
            sourceTextView = itemView.findViewById(R.id.tv_article_source);
            publishDateTextView = itemView.findViewById(R.id.tv_publish_date);
            this.onArticleListener = onArticleListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onArticleListener.onArticleClick(getAdapterPosition());

        }
    }


    public interface OnArticleListener {
        void onArticleClick(int position);
    }
}
