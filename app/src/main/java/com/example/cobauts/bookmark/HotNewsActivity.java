package com.example.cobauts.bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.cobauts.R;
import com.example.cobauts.adapter.NewsAdapter;
import com.example.cobauts.like.NewsItem;

import java.util.ArrayList;

public class HotNewsActivity extends AppCompatActivity {

    private ArrayList<NewsItem> newsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_news);

        RecyclerView recyclerView = findViewById(R.id.rv_likes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NewsAdapter(this, newsItems));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsItems.add(new NewsItem("Berita 1", "coba berita","0","0"));
        newsItems.add(new NewsItem("Berita 2", "coba berita 2","1","0"));

    }
}