package com.sourcey.KauLempung.User;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sourcey.KauLempung.Adapter.ProdukAdapter;
import com.sourcey.KauLempung.Model.Produk;
import com.sourcey.KauLempung.R;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner filterPrice;
    RecyclerView rvProduk;
    DatabaseReference ref;
    ProdukAdapter produkAdapter;
    List<Produk> mList;
    List<Produk> produkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterPrice = findViewById(R.id.spinner_harga);
        rvProduk = findViewById(R.id.rv_produk);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter_harga, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterPrice.setAdapter(adapter);
        filterPrice.setOnItemSelectedListener(this);

        rvProduk.setHasFixedSize(true);
        rvProduk.setLayoutManager(new GridLayoutManager(this, 2));
        mList = new ArrayList<>();
        produkList = new ArrayList<>();
        produkAdapter = new ProdukAdapter(this, mList);
        rvProduk.setAdapter(produkAdapter);

        ref = FirebaseDatabase.getInstance().getReference().child("katalogproduk");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Produk produk = data.getValue(Produk.class);
                    mList.add(produk);
                    produkList.add(produk);
                    produkAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        if (adapterView.getItemAtPosition(pos).equals("Harga")){
            mList.clear();
            mList.addAll(produkList);
            produkAdapter.notifyDataSetChanged();
        }else{
            int itemId = (int) adapterView.getItemIdAtPosition(pos);
            switch (itemId) {
                case 1:
                    mList.clear();
                    for (Produk produk : produkList) {
                        if (Integer.parseInt(produk.harga) >= 10000 && Integer.parseInt(produk.harga) < 50000) {
                            mList.add(produk);
                        }
                    }
                    produkAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    mList.clear();
                    for (Produk produk : produkList) {
                        if (Integer.parseInt(produk.harga) >= 50000 && Integer.parseInt(produk.harga) < 100000) {
                            mList.add(produk);
                        }
                    }
                    produkAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    mList.clear();
                    for (Produk produk : produkList) {
                        if (Integer.parseInt(produk.harga) >= 100000 && Integer.parseInt(produk.harga) < 200000) {
                            mList.add(produk);
                        }
                    }
                    produkAdapter.notifyDataSetChanged();
                    break;
                case 4:
                    mList.clear();
                    for (Produk produk : produkList) {
                        if (Integer.parseInt(produk.harga) >= 200000 && Integer.parseInt(produk.harga) < 300000) {
                            mList.add(produk);
                        }
                    }
                    produkAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
