package com.hugh.lelele.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.component.GridSpacingItemDecoration;
import com.hugh.lelele.data.Article;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeFragment extends Fragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    private HomeContract.Presenter mPresenter;
    private HomeAdapter mHomeAdapter;
    private ArrayList<Article> mArticles;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView mNoArticleText;

    private final String TAG = LeLeLe.class.getSimpleName();

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.loadArticles();
//        mSwipeRefreshLayout.setRefreshing(true);
        mHomeAdapter = new HomeAdapter(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_home_articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mHomeAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,
                getContext().getResources().getDimensionPixelOffset(R.dimen.space_detail_circle), true));

        mSwipeRefreshLayout = root.findViewById(R.id.swipe_container_home);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mNoArticleText = root.findViewById(R.id.text_view_no_article_home);

        setNoArticleTextStatus();

        return root;
    }

    private void setNoArticleTextStatus() {

        if (mArticles == null || mArticles.size() == 0) {
            mNoArticleText.setVisibility(View.VISIBLE);
        } else {
            mNoArticleText.setVisibility(View.GONE);
        }
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setArticleList(ArrayList<Article> articleList) {
        mArticles = articleList;
        mSwipeRefreshLayout.setRefreshing(false);
        setNoArticleTextStatus();
        if (mHomeAdapter == null) {
            mHomeAdapter = new  HomeAdapter(mPresenter);
            mHomeAdapter.updateArticles(articleList);
        } else {
            mHomeAdapter.updateArticles(articleList);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.loadArticles();
        mSwipeRefreshLayout.setRefreshing(true);
    }
}
