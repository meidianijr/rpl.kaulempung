package com.sourcey.KauLempung.User;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sourcey.KauLempung.Adapter.ProdukAdapter;
import com.sourcey.KauLempung.Adapter.ProdukViewHolder;
import com.sourcey.KauLempung.Adapter.SearchProdukAdapter;
import com.sourcey.KauLempung.LoginActivity;
import com.sourcey.KauLempung.Model.Produk;
import com.sourcey.KauLempung.Model.Produk2;
import com.sourcey.KauLempung.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class DaftarKatalog extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CarouselView carouselView;
    int[] sampleImages = { R.drawable.image_3, R.drawable.image_2, R.drawable.image_4, R.drawable.image_1, R.drawable.image_5};
    private FirebaseAuth mAuth;

    private EditText editText;
    private RecyclerView recyclerView;

    DatabaseReference ref;

    ArrayList<Produk> list;

    ProdukAdapter katalogAdapter;

    String idUser;

    private TextView tvFilter;
    Spinner filterPrice;
    List<Produk> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_katalog);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            tvFilter = findViewById(R.id.tv_filter_harga);
            tvFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(DaftarKatalog.this, FilterActivity.class));
                }
            });

            filterPrice = findViewById(R.id.spinner_harga);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter_harga, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            filterPrice.setAdapter(adapter);
            filterPrice.setOnItemSelectedListener(this);


            carouselView = findViewById(R.id.carouselView);
            carouselView.setPageCount(sampleImages.length);
            carouselView.setImageListener(imageListener);
            idUser = user.getEmail();

            ref = FirebaseDatabase.getInstance().getReference().child("katalogproduk");

            list = new ArrayList<>();
            mList = new ArrayList<>();
            katalogAdapter = new ProdukAdapter(this,list);

            recyclerView = findViewById(R.id.list_recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.setAdapter(katalogAdapter);

            editText = findViewById(R.id.searchproduk);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Produk cur = data.getValue(Produk.class);
                        cur.key = data.getKey();
                        list.add(cur);
                        mList.add(cur);
                        katalogAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (!s.toString().isEmpty()) {
                        setAdapter(s.toString());
                    }else{
                        list.addAll(mList);
                        katalogAdapter.notifyDataSetChanged();
                    }

                }
            });

        }
    }

    private void setAdapter(String s) {
        list.clear();
        for (Produk produk : mList){
            if (produk.title.toLowerCase().contains(s.toLowerCase())){
                list.add(produk);
            }
            katalogAdapter.notifyDataSetChanged();
        }
    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mAuth.signOut();
            startActivity(new Intent(DaftarKatalog.this, LoginActivity.class));
            finish();
        } else if (id == R.id.action_profile){
            startActivity(new Intent(DaftarKatalog.this,ProfilUser.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getItemAtPosition(i).equals("Harga")){
            list.clear();
            list.addAll(mList);
            katalogAdapter.notifyDataSetChanged();
        }else {
            int itemId = (int) adapterView.getItemIdAtPosition(i);
            switch (itemId) {
                case 1:
                    list.clear();
                    for (Produk produk : mList) {
                        if (Integer.parseInt(produk.harga) >= 10000 && Integer.parseInt(produk.harga) < 50000) {
                            list.add(produk);
                        }
                    }
                    katalogAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    list.clear();
                    for (Produk produk : mList) {
                        if (Integer.parseInt(produk.harga) >= 50000 && Integer.parseInt(produk.harga) < 100000) {
                            list.add(produk);
                        }
                    }
                    katalogAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    list.clear();
                    for (Produk produk : mList) {
                        if (Integer.parseInt(produk.harga) >= 100000 && Integer.parseInt(produk.harga) < 200000) {
                            list.add(produk);
                        }
                    }
                    katalogAdapter.notifyDataSetChanged();
                    break;
                case 4:
                    list.clear();
                    for (Produk produk : mList) {
                        if (Integer.parseInt(produk.harga) >= 200000 && Integer.parseInt(produk.harga) < 300000) {
                            list.add(produk);
                        }
                    }
                    katalogAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
