package com.example.virus.saver.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virus.saver.ProfileActivity;
import com.example.virus.saver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{
    EditText loget1,loget2;
    TextView logtv1,logtv2;
    Button logb1;
    //DatabaseReference reference;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loget1=findViewById(R.id.loget1);
        loget2=findViewById(R.id.loget2);
        logb1=findViewById(R.id.logb1);
        logtv1=findViewById(R.id.logtv1);
        logtv2=findViewById(R.id.logtv2);

        progressDialog=new ProgressDialog(this);
        // initializing FirebaseAuth Object
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            //profile activity here
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
            finish();
        }

    }
    public void login(View view)
    {
        //getting email and password from edit texts
        String email=loget1.getText().toString().trim();
        String password=loget2.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(LoginActivity.this, "Enter Both Username & Password", Toast.LENGTH_LONG).show();
            return;
        }
        //displaying a progress dialog
        progressDialog.setMessage("Registration in progress plz wait");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "Sign In Success", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this,ProfileActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials Failure", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    public void forward1(View view)
    {
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void forgot(View view)
    {
        Intent i=new Intent(this,ForgotPassword.class);
        startActivity(i);
        finish();
    }
}
