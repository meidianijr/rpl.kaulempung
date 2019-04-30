package com.sourcey.KauLempung.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sourcey.KauLempung.Model.Produk;
import com.sourcey.KauLempung.R;

public class DetailKatalog extends AppCompatActivity {

    TextView aa,bb,cc,dd,ee;
    ImageView ff;

    DatabaseReference databaseFood;

    FirebaseAuth mAuth;

    String stringUri;


    private Uri imageUri;


    private StorageReference mStorage;

    Query databaseUser;

    ProgressDialog dlg;

    String idCurrentUser;

    String id;

    String idUser;

    private String postKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_katalog);

        aa = findViewById(R.id.namaprdk);
        bb = findViewById(R.id.harga);
        cc = findViewById(R.id.namaprodusen);
        dd = findViewById(R.id.alamat);
        ee = findViewById(R.id.nohpprod);

        ff = findViewById(R.id.imageView);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        idCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        idUser = getIntent().getStringExtra("key");
        dlg = new ProgressDialog(this);

        mStorage = FirebaseStorage.getInstance().getReference();

        tampildata();
    }

    private void tampildata() {
        databaseFood = FirebaseDatabase.getInstance().getReference();
        databaseFood.child("katalogproduk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Produk cur = data.getValue(Produk.class);
                    if (cur.getTitle().equals(String.valueOf(idUser))) {
                        postKey = data.getKey();
                        cur.setKey(postKey);
                        aa.setText(cur.getTitle());
                        bb.setText(cur.getHarga());
                        cc.setText(cur.getNamaprod());
                        ee.setText(cur.getNohp());
                        dd.setText(cur.getAlamat());
                        Glide.with(getApplication()).load(cur.getImage()).into(ff);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void edit(View view) {
        Intent al = new Intent(DetailKatalog.this,EditKatalog.class);
        al.putExtra("key",idUser);
        startActivity(al);
    }

    public void hapus(View view) {
        databaseFood = FirebaseDatabase.getInstance().getReference();
        databaseFood.child("katalogproduk").child(postKey).removeValue();
        finish();
    }
}
