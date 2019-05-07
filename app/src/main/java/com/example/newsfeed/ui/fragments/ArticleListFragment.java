package com.example.newsfeed.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsfeed.ui.activities.ArticleDetailsActivity;
import com.example.newsfeed.viewModel.MainViewModel;
import com.example.newsfeed.R;
import com.example.newsfeed.adapter.ArticleAdapter;
import com.example.newsfeed.data.model.Article;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticleListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleListFragment extends Fragment implements ArticleAdapter.OnArticleListener {

    private static final String TAG = ArticleListFragment.class.getSimpleName();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainViewModel mainViewModel;

    private ArticleAdapter articleAdapter;
    private List<Article> articleList;

    private RecyclerView articleRecyclerView;


    private OnFragmentInteractionListener mListener;

    public ArticleListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleListFragment newInstance(/*String param1, String param2*/) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        articleRecyclerView = view.findViewById(R.id.rv_articles);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);


        articleList = new ArrayList<>();

        setUpRecyclerView(articleList);

        mainViewModel.getArticleListLiveData().observe(this, articles -> {

            Log.d(TAG, "getArticleListLiveData: Data refreshed");
            Log.d(TAG, "getArticleListLiveData: " + articles.toString());

            articleList.clear();
            articleList.addAll(articles);
            articleAdapter.notifyDataSetChanged();

        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onArticleClick(int position) {
        Log.d(TAG, "onArticleClick: " + position + ": " + articleList.get(position).toString());

        mainViewModel.setSelectedArticleLiveData(articleList.get(position));
        mainViewModel.setShowArticleDetailsLiveData(true);

        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("ArticleObject", articleList.get(position));
        startActivity(intent);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /**
     * Method to initialize the recyclerview and its components.
     */
    public void setUpRecyclerView(List<Article> mArticleList) {

        GridLayoutManager articleGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        articleRecyclerView.setLayoutManager(articleGridLayoutManager);
        articleRecyclerView.setHasFixedSize(true);
        articleAdapter = new ArticleAdapter(getActivity(), mArticleList, this);
        articleRecyclerView.setAdapter(articleAdapter);

    }
}
