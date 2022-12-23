package com.example.cobauts.adapter;

import android.content.Context;
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
import com.example.cobauts.like.LikeItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {

    private Context context;
    private List<LikeItem> likeItemList;
    private LikeDB likeDB;
    private DatabaseReference refLike;

    public LikeAdapter(Context context, List<LikeItem> likeItemList) {
        this.context = context;
        this.likeItemList = likeItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like,
                parent, false);
        likeDB = new LikeDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.likeTvTitle.setText(likeItemList.get(position).getItem_title());
        holder.likeTvDesc.setText(likeItemList.get(position).getItem_desc());
    }

    @Override
    public int getItemCount() {
        return likeItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView likeTvTitle, likeTvDesc;
        Button favBtn, btnBookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            likeTvTitle = itemView.findViewById(R.id.likeTvTitle);
            likeTvDesc = itemView.findViewById(R.id.likeTvDesc);
            favBtn = itemView.findViewById(R.id.favBtn);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
            favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);


            refLike = FirebaseDatabase.getInstance().getReference().child("likdes");

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final LikeItem likeItem = likeItemList.get(position);

                    final DatabaseReference upvotesRefLike = refLike.child(likeItemList.get(position).getKey_id());
                    likeDB.remove_fav(likeItem.getKey_id());
                    removeItem(position);

                    upvotesRefLike.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            try{
                                Integer currentValue = mutableData.getValue(Integer.class);
                                if(currentValue == null){
                                    mutableData.setValue(1);
                                } else {
                                    mutableData.setValue(currentValue - 1);
                                }
                            }catch (Exception e){
                                throw e;
                            }
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                            System.out.println("Transaction Completed");
                        }
                    });
                }
            });
        }
    }

    private void removeItem(int position) {
        likeItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,likeItemList.size());
    }
}
