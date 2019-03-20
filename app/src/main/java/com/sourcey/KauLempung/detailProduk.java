package com.sourcey.KauLempung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class detailProduk extends AppCompatActivity {

    private TextView namaProdusen, namaDetail, hargaDetail, alamatDetail, teleponDetail;
    private ImageView fotoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);



        namaProdusen = findViewById(R.id.namaProdusen);
        namaDetail = findViewById(R.id.namaBarang);
        hargaDetail = findViewById(R.id.harga);
        alamatDetail = findViewById(R.id.alamatDetail);
        teleponDetail = findViewById(R.id.teleponDetail);
        fotoDetail = findViewById(R.id.fotoDetail);

        String namaKendi = getIntent().getStringExtra("nama");
        String harga = String.valueOf(getIntent().getStringExtra("harga"));
        namaDetail.setText(namaKendi);
        hargaDetail.setText(harga);
        fotoDetail.setImageResource(getIntent().getIntExtra("foto", 2));

    }
}
