package com.example.virus.saver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virus.saver.Auth.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity
{
    TextView profilet1;
    Button b1,b2;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilet1=findViewById(R.id.pofiletv1);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null)
        {
            Intent i=new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        }
        FirebaseUser currentuser=auth.getCurrentUser();
        profilet1.setText("Welcome  "+"\n"+currentuser.getEmail());
        checkEmailVerified();

    }

    public void logout(View view)
    {

        auth.signOut();
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }


    public void verify(View view)
    {
        final FirebaseUser currentuser=FirebaseAuth.getInstance().getCurrentUser();
        currentuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Verification email sent to" + "\n" +currentuser.getEmail(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ProfileActivity.this, "Try Again To Verify", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkEmailVerified()
    {
        FirebaseUser currentuser=FirebaseAuth.getInstance().getCurrentUser();
        if (currentuser.isEmailVerified())
        {
            b2.setVisibility(View.GONE);
            Toast.makeText(this, "WELCOME ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Verify your email Address", Toast.LENGTH_LONG).show();
        }
    }
}
