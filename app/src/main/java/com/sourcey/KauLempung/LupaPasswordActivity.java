package com.sourcey.KauLempung;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;


public class LupaPasswordActivity extends AppCompatActivity {

    private Button btn_reset;
    private EditText reset_email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reset_email = findViewById(R.id.email_forgot);
        btn_reset = findViewById(R.id.btn_sendEmail);
    }
    }

