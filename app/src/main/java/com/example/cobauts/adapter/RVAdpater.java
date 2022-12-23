package com.example.cobauts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cobauts.RV.DAONews;
import com.example.cobauts.activity.AddNewsActivity;
import com.example.cobauts.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cobauts.data.News;

import java.util.ArrayList;

public class RVAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<News> list = new ArrayList<>();

    public RVAdpater(Context ctx){
        this.context = ctx;
    }

    public void setItems(ArrayList<News> newsList){
        list.addAll(newsList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);
        return new NewsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        News e = null;
        this.onBindViewHolder(holder,position,e);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, News e) {
        NewsVH vh = (NewsVH) holder;
        News news = e==null? list.get(position):e;
        vh.tvJudul.setText(news.getTitle());
        vh.tvdesc.setText(news.getDesc());
        vh.tvTanggal.setText(news.getTanggal());

        vh.txt_option.setOnClickListener(v -> {

            PopupMenu popupMenu =new PopupMenu(context,vh.txt_option);
            popupMenu.inflate(R.menu.options_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.menu_edit:
                        Intent intent = new Intent(context, AddNewsActivity.class);
                        intent.putExtra("Edit", news);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        DAONews dao = new DAONews();
                        dao.remove(news.getKey()).addOnSuccessListener(suc->
                        {
                            Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            list.remove(news);
                        }).addOnFailureListener(er->
                        {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsVH extends RecyclerView.ViewHolder {

        TextView tvTanggal, tvJudul, tvdesc, txt_option, likeCountTextView;
        Button btnBookmark, btnLike;

        public NewsVH(@NonNull View itemView){
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_item_title);
            tvdesc = itemView.findViewById(R.id.tv_item_desc);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
            txt_option = itemView.findViewById(R.id.txt_option);
            likeCountTextView = itemView.findViewById(R.id.likeCountTextView);
        }
    }
}
