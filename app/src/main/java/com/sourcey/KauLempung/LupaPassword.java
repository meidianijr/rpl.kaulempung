package com.sourcey.KauLempung;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LupaPassword extends AppCompatActivity {


    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        passwordEmail = (EditText)findViewById(R.id.input_email);
        resetPassword = (Button)findViewById(R.id.btn_forgot);
        firebaseAuth = FirebaseAuth.getInstance();


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usermail = passwordEmail.getText().toString().trim();

                if(usermail.equals("")){
                    Toast.makeText(LupaPassword.this, "Masukan Email yang sudah terdaftar", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LupaPassword.this, "Password sudah terkirim ke email", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(LupaPassword.this, LoginActivity.class));
                            }else{
                                Toast.makeText(LupaPassword.this, "Error pengiriman password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}
