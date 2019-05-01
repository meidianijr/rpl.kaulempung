package com.sourcey.KauLempung.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sourcey.KauLempung.LoginActivity;
import com.sourcey.KauLempung.Model.Produk;
import com.sourcey.KauLempung.R;

import java.util.List;

public class ProdukAdapterNonCheck extends RecyclerView.Adapter<ProdukAdapterNonCheck.ItemViewHolder> {

    //definisi untuk ArrayList -> ini kita gunakan krena kita menggunkan list
    public List<Produk> mExampleList;

    //definis untuk context -> sama seperti 'this' kita gunakan untuk menunjukkan identitas kelas dimana dia berada
    // dan karena kita menggunakan intent maka perlu ada nya ini
    Context mContext;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitlePost;

        public TextView mPost;

        public TextView mDetail;

        public ImageView mImagePost;

        public CardView cardViewPost;


        public  ItemViewHolder(View itemView){
            super(itemView);
            mTitlePost = itemView.findViewById(R.id.tv_title_post);

            mPost = itemView.findViewById(R.id.tv_post);
            mDetail = itemView.findViewById(R.id.detail);

            mImagePost=itemView.findViewById(R.id.img_post);

            cardViewPost= itemView.findViewById(R.id.cardViewPost);

        }
    }

    public ProdukAdapterNonCheck(Context context, List<Produk> exampleItems){

        mExampleList = exampleItems;
        mContext = context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_katalog,parent,false);
        ItemViewHolder evh = new ItemViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {

         final Produk currentItem = mExampleList.get(position);

//         String[] user = currentItem.user.split("@");
//
//        holder.mUsername.setText(user[0]);
        holder.mTitlePost.setText(currentItem.getTitle());
        holder.mPost.setText("Rp. "+currentItem.getHarga());
        holder.mPost.setTag(currentItem.getImage());

        Glide.with(mContext).load(currentItem.getImage()).into(holder.mImagePost);

        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                Intent kk = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(kk);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
