package com.example.devsmar.dailynews;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.devsmar.dailynews.Adapter.NewsAdapter;
import com.example.devsmar.dailynews.Model.NewsFeed;
import com.example.devsmar.dailynews.Service.NewsFeedServiceImpl;
import com.example.devsmar.dailynews.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsFeed>> ,NewsAdapter.NewsClickListener{

    private static final int LOADER_ID = 0;
    List<NewsFeed> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(this, arrayList, this);
        recyclerView.setAdapter(newsAdapter);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public Loader<List<NewsFeed>> onCreateLoader(int id, Bundle args) {
        if (Utility.isNetworkAvailable(this)) {
            return new NewsFeedServiceImpl(this);
        }
        else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<NewsFeed>> loader, List<NewsFeed> data) {
        if (data != null) {
            arrayList.addAll(data);
            newsAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsFeed>> loader) {
        arrayList.clear();
        newsAdapter.notifyItemRangeRemoved(0,arrayList.size());
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(arrayList.get(position).getWebUrl()));
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {

    }
}
