package com.sourcey.KauLempung;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;

public class LupaPasswordActivity extends AppCompatActivity {

    EditText mEmailText;
    Button mSubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);


        mEmailText = (EditText)findViewById(R.id.editText);
        mSubmitButton = (Button)findViewById(R.id.button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String email = mEmailText.getText().toString();
                email.trim();

                if(email.isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LupaPasswordActivity.this);
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {

                    setProgressBarIndeterminateVisibility(true);
                    ParseUser.requestPasswordResetInBackground(email,
                            new RequestPasswordResetCallback() {


                                public void done(ParseException e) {
                                    // TODO Auto-generated method stub
                                    setProgressBarIndeterminateVisibility(false);
                                    if (e == null) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(LupaPasswordActivity.this);
                                        builder.setMessage("An email was sent. Please check your email within the next few minutes for more instructions")
                                                .setTitle("Success")
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LupaPasswordActivity.this);
                                        builder.setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });
                }

            }
        });


    }

}

