package com.example.cobauts.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cobauts.R;
import com.example.cobauts.like.LikeDB;
import com.example.cobauts.like.NewsItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {

    private Context context;
    private LikeDB likeDB;
    ArrayList<NewsItem> newsItems;

    public NewsAdapter(Context context, ArrayList<NewsItem> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        likeDB = new LikeDB(context);
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart){
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        final NewsItem newsItem = newsItems.get(position);
        readCursorData(newsItem, holder);

        holder.tvJudul.setText(newsItem.getTitle());
        holder.tvdesc.setText(newsItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTanggal, tvJudul, tvdesc, txt_option, likeCountTextView;
        Button btnBookmark, btnLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tv_item_title);
            tvdesc = itemView.findViewById(R.id.tv_item_desc);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
            txt_option = itemView.findViewById(R.id.txt_option);
            likeCountTextView = itemView.findViewById(R.id.likeCountTextView);

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    NewsItem newsItem = newsItems.get(position);
                    likeClick(newsItem, btnLike, likeCountTextView);
                }
            });

//            btnBookmark.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    NewsItem newsItem = newsItems.get(position);
//                    likeClick(newsItem, btnLike, likeCountTextView);
//                }
//            });
        }
    }

    private void createTableOnFirstStart() {
        likeDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(NewsItem news, NewsAdapter.ViewHolder viewHolder) {
        Cursor cursor = likeDB.read_all_data(news.getKey());
        SQLiteDatabase db = likeDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String item_fav_status = cursor.getString(cursor.getColumnIndex(LikeDB.FAVORITE_STATUS));
                news.setLikeStatus(item_fav_status);
                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }

    // like click
    private void likeClick (NewsItem newsItem, Button favBtn, final TextView textLike) {
        DatabaseReference refLike = FirebaseDatabase.getInstance().getReference().child("likes");
        final DatabaseReference upvotesRefLike = refLike.child(newsItem.getKey());

        if (newsItem.getLikeStatus().equals("0")) {

            newsItem.setLikeStatus("1");
            likeDB.insertIntoTheDatabase(newsItem.getTitle(), newsItem.getDesc(),
                    newsItem.getKey(), newsItem.getLikeStatus());
            favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            favBtn.setSelected(true);

            upvotesRefLike.runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull final MutableData mutableData) {
                    try {
                        Integer currentValue = mutableData.getValue(Integer.class);
                        if (currentValue == null) {
                            mutableData.setValue(1);
                        } else {
                            mutableData.setValue(currentValue + 1);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    textLike.setText(mutableData.getValue().toString());
                                }
                            });
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                    System.out.println("Transaction completed");
                }
            });
        } else if (newsItem.getLikeStatus().equals("1")) {
            newsItem.setLikeStatus("0");
            likeDB.remove_fav(newsItem.getKey());
            favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            favBtn.setSelected(false);

            upvotesRefLike.runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull final MutableData mutableData) {
                    try {
                        Integer currentValue = mutableData.getValue(Integer.class);
                        if (currentValue == null) {
                            mutableData.setValue(1);
                        } else {
                            mutableData.setValue(currentValue - 1);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    textLike.setText(mutableData.getValue().toString());
                                }
                            });
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                    System.out.println("Transaction completed");
                }
            });
        }
    }
}
