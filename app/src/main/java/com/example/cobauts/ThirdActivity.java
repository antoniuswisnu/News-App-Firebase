package com.example.cobauts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {

    private RecyclerView rvNews;
    private ArrayList<News> list = new ArrayList<>();
    TextView tvJudul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        rvNews = findViewById(R.id.rv_news);
        tvJudul = findViewById(R.id.tvJudul);

        Intent intent= getIntent();
        String message = intent.getStringExtra(CariBeritaActivity.MESSAGE_EXTRA);
        int ageData = intent.getIntExtra("kodeUmur", 1);
        tvJudul.setText(message);

        if(Objects.equals(message, "sports") && ageData < 17){
            list.addAll(NewsData.getListDataSportsLow());
        } else if(Objects.equals(message, "sports") && ageData >= 17){
            list.addAll(NewsData.getListDataSports());
            list.addAll(NewsData.getListDataSportsLow());
        } else if (Objects.equals(message, "politics") && ageData >= 17 ){
            list.addAll(NewsData.getListDataPolitics());
            list.addAll(NewsData.getListDataPoliticsLow());
        }else if (Objects.equals(message, "politics") && ageData < 17){
            list.addAll(NewsData.getListDataPoliticsLow());
        } else if (Objects.equals(message, "entertainment") && ageData >= 17 ){
            list.addAll(NewsData.getListDataEntertainment());
            list.addAll(NewsData.getListDataEntertainmentLow());
        }else if (Objects.equals(message, "entertainment") && ageData < 17){
            list.addAll(NewsData.getListDataEntertainmentLow());
        }
        showRecyclerList();
    }

    private void showRecyclerList(){
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter listAdapter = new ListAdapter(list);
        rvNews.setAdapter(listAdapter);
    }

}