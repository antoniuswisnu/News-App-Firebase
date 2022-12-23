package com.example.cobauts.RV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import com.example.cobauts.R;
import com.example.cobauts.activity.CariBeritaActivity;
import com.example.cobauts.adapter.RVAdpater;
import com.example.cobauts.data.News;
import com.example.cobauts.like.NewsItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {

    RecyclerView rvNews;
    DAONews dao;
    RVAdpater adapter;
    String key = null;
    boolean isLoading = false;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<NewsItem> newsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        swipeRefreshLayout = findViewById(R.id.swip);

        rvNews = findViewById(R.id.rv_news);
        rvNews.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvNews.setLayoutManager(manager);
        adapter = new RVAdpater(this);
        rvNews.setAdapter(adapter);
        dao = new DAONews();
        loadData();

        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rvNews.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (totalItem < lastVisible + 3) {
                    if (!isLoading){
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });
    }

    private void loadData()
    {
        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Intent intent= getIntent();
                String message = intent.getStringExtra(CariBeritaActivity.MESSAGE_EXTRA);
                int ageData = intent.getIntExtra("age", 1);
                News berita = new News();
                ArrayList<News> newss = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren())
                {
                    News neww = data.getValue(News.class);
                    neww.setKey(data.getKey());
                    newss.add(neww);
                    key = data.getKey();

                }
                adapter.setItems(newss);
                adapter.notifyDataSetChanged();
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}