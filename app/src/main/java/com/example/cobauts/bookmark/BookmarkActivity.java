package com.example.cobauts.bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.cobauts.R;
import com.example.cobauts.adapter.LikeAdapter;
import com.example.cobauts.like.LikeDB;
import com.example.cobauts.like.LikeItem;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LikeDB likeDB;
    private List<LikeItem> likeItemList = new ArrayList<>();
    private LikeAdapter likeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        likeDB = new LikeDB(this);
        recyclerView = findViewById(R.id.rvBookmark);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadData();
    }

    private void loadData(){
        if (likeItemList != null){
            likeItemList.clear();
        }
        SQLiteDatabase db = likeDB.getReadableDatabase();
        Cursor cursor = likeDB.select_all_favorite_list();
        try{
            while (cursor.moveToNext()){
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(LikeDB.ITEM_TITLE));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(LikeDB.KEY_ID));
                @SuppressLint("Range") String desc = cursor.getString(cursor.getColumnIndex(LikeDB.ITEM_DESC));
                LikeItem likeItem = new LikeItem(title, id, desc);
                likeItemList.add(likeItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
        likeAdapter = new LikeAdapter(this, likeItemList);
        recyclerView.setAdapter(likeAdapter);
    }
}