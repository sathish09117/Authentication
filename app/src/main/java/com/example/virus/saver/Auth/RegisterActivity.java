package com.example.virus.saver.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.virus.saver.Auth.Model.SaveRegisteredDetails;
import com.example.virus.saver.ProfileActivity;
import com.example.virus.saver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class RegisterActivity extends AppCompatActivity
{
    EditText reg_email,reg_pwd,reg_cpwd;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    ProSwipeButton proSwipeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_email=findViewById(R.id.reg_email);
        reg_pwd=findViewById(R.id.reg_pwd);
        reg_cpwd=findViewById(R.id.reg_cpwd);

        progressDialog=new ProgressDialog(this);

        // initializing FirebaseAuth Object
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            //profile activity here
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
            finish();
        }

        proSwipeLogin = findViewById(R.id.proswipebutton_login);
        proSwipeLogin.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        register();
                    }
                },2000);



            }
        });


    }
    public void register()
    {
        //getting email and password from edit texts
        final String email=reg_email.getText().toString().trim();
        final String passsword=reg_pwd.getText().toString().trim();
        String cnfpass=reg_cpwd.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)  || TextUtils.isEmpty(passsword) || TextUtils.isEmpty(cnfpass))
        {
            proSwipeLogin.showResultIcon(false);
            Toast.makeText(RegisterActivity.this, "Please Fill all Fields", Toast.LENGTH_LONG).show();
            return;
        }
        if (passsword.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passsword.equals(cnfpass))
        {

            Toast.makeText(this, "password matches", Toast.LENGTH_SHORT).show();
        }
        else
        {
            proSwipeLogin.showResultIcon(false);
            Toast.makeText(this, "Both Passwords Doesnt Match", Toast.LENGTH_SHORT).show();
            return;
        }
        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registration in progress plz wait");
        progressDialog.show();

        //creating a new user
        auth.createUserWithEmailAndPassword(email,passsword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    //save registered details in fire base data base
                    SaveRegisteredDetails details=new SaveRegisteredDetails(email,passsword);
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
                    reference.child("Users Data").push().setValue(details);

                    proSwipeLogin.showResultIcon(true);

                    //display some message here
                    Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                    reg_email.setText("");
                    reg_pwd.setText("");
                    reg_cpwd.setText("");

                    /*Forwading to current Profile*/
                    Intent i = new Intent(RegisterActivity.this, ProfileActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    proSwipeLogin.showResultIcon(false);
                    //display some message here
                    Toast.makeText(RegisterActivity.this, "Email is Already Used", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });

    }





    public void forward(View view)
    {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }


}
