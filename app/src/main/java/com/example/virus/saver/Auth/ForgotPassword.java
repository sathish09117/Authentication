package com.example.virus.saver.Auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.virus.saver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity
{

    EditText et1;
    Button b1;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        et1=findViewById(R.id.et1);
        b1=findViewById(R.id.b1);
        auth=FirebaseAuth.getInstance();

    }

    public void reset(View view)
    {
        String email=et1.getText().toString().trim();

        if (email.isEmpty())
        {
            et1.setError("Empty");
            et1.requestFocus();
        }


      else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, "Veification Email has Sent", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(ForgotPassword.this, "Email Not Registered with Our DataBase", Toast.LENGTH_LONG).show();
                        et1.setText("");
                        et1.requestFocus();
                    }
                }
            });
        }
    }

    public void back(View view)
    {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}
