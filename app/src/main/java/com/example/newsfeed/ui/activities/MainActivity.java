package com.example.newsfeed.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.newsfeed.data.AppRepository;
import com.example.newsfeed.data.database.AppDatabase;
import com.example.newsfeed.ui.fragments.ArticleListFragment;
import com.example.newsfeed.util.MyApp;
import com.example.newsfeed.viewModel.MainViewModel;
import com.example.newsfeed.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel mainViewModel;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.my_toolbar) Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        ArticleListFragment articleListFragment = ArticleListFragment.newInstance();
        fragmentTransaction
                .replace(R.id.fragment_container, articleListFragment)
                .commit();

        mainViewModel.getTitleLiveData().observe(this, integer -> {

            if (integer != null) {
                setTitle(integer);
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_refresh:
                mainViewModel.setTitleLiveData(R.string.top_headlines_label);
                mainViewModel.loadArticlesFromApi();
                return true;

            case R.id.action_top_headlines:
                mainViewModel.setTitleLiveData(R.string.top_headlines_label);
                mainViewModel.loadArticlesFromApi();
                return true;

            case R.id.action_bookmarks:
                mainViewModel.setTitleLiveData(R.string.bookmark_label);
                AppDatabase.getInstance(MyApp.getAppContext()).bookmarkedArticleDao().getAllArticles().observe(this, articles ->
                        AppRepository.getInstance().setArticleListLiveData(articles));
                return true;

            case R.id.action_logout:
                navigateToLogin();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Method to go back to the Login Activity.
     */
    public void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
