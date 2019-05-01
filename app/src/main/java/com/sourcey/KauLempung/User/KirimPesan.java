package com.sourcey.KauLempung.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.sourcey.KauLempung.R;

public class KirimPesan extends AppCompatActivity {

    EditText Edtnama, Edalamat, Edtkodepos, Edtbarang, Edtjumlah;

    String a,b,c,d,e;

    String idCurrentUser;

    String id;

    SharedPreferences spNight;

    final String PREF_NIGHT_MODE = "NightMode";

    SharedPreferences.Editor editNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        spNight = getSharedPreferences(PREF_NIGHT_MODE , Context.MODE_PRIVATE);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.AppTheme_Dark);
        }else{
            setTheme(R.style.AppTheme);

            if(spNight.getBoolean(PREF_NIGHT_MODE,false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_pesan);

        Edtnama = findViewById(R.id.edit_nama);
        Edalamat = findViewById(R.id.edit_alamat);
        Edtkodepos = findViewById(R.id.edit_pos);
        Edtbarang = findViewById(R.id.edit_barang);
        Edtjumlah = findViewById(R.id.edit_jmlh);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        idCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        a = getIntent().getStringExtra("namaproduk");
        b = getIntent().getStringExtra("telepon");

        Edtbarang.setText(String.valueOf(a));

    }

    public void kirim(View view) {
        String pesan1 = Edtnama.getText().toString();
        String pesan2 = Edalamat.getText().toString();
        String pesan3 = Edtkodepos.getText().toString();
        String pesan4 = Edtbarang.getText().toString();
        String pesan5 = Edtjumlah.getText().toString();
        String notelepon = String.valueOf(b);

        String semuapesan = "Nama: " + pesan1 + "\n" + "Alamat : " + pesan2 + "\n" + "Kode Pos : " + pesan3 + "\n" + "Nama Barang : " + pesan4 + "\n" + "Jumlah Pemesanan : " + pesan5;

        Intent kirimWA = new Intent(Intent.ACTION_SEND);
        kirimWA.setType("text/plain");
        kirimWA.putExtra(Intent.EXTRA_TEXT, semuapesan);
        kirimWA.putExtra("jid", notelepon + "@s.whatsapp.net");
        kirimWA.setPackage("com.whatsapp");

        startActivity(kirimWA);
    }
}
