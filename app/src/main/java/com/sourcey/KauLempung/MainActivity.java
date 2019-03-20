package com.sourcey.KauLempung;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sourcey.KauLempung.Adapter.ItemAdapter;
import com.sourcey.KauLempung.Model.Item;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> exampleItems;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    EditText search;


    FirebaseAuth.AuthStateListener listener;
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast") //dari search
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (EditText)findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() { //untuk SEARCH
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!s.toString().isEmpty())
                {
                    searching(s.tiString());
                }
                else
                {
                    searching("");
                }

            }
        });


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            exampleItems = new ArrayList<>();
            exampleItems.add(new Item(R.drawable.dandang, "Dandang", "Rp55.000"));
            exampleItems.add(new Item(R.drawable.gerabah, "Gerabah", "Rp70.000"));
            exampleItems.add(new Item(R.drawable.guci, "Guci", "Rp95.000"));
            exampleItems.add(new Item(R.drawable.panci, "Panci", "Rp25.000"));
            buildRecyclerView();
        }
    }

//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.menuLogout :
                FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
            break;
        }
        return true;
    }

    private void buildRecyclerView() {
        int gridColoumnCount = getResources().getInteger(R.integer.grid_coloumn_count);

        mRecyclerView = findViewById(R.id.rv);

        mAdapter = new ItemAdapter(this,exampleItems);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,gridColoumnCount));
        mRecyclerView.setAdapter(mAdapter);

        int swipeDirs;
        if(gridColoumnCount > 1){
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper //Method hapus
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                swipeDirs) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(exampleItems, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
                exampleItems.remove(viewHolder.getAdapterPosition()); //Menghapus kartu
                // Notify the adapter.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        } else if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }
    }


    public void searching (String s){
        Query query = databaseRerefence.orderByChild("name")
                .starAt (s)
                .endAt (s + "\uf8ff");
        
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (DataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
    }

}
