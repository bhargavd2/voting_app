package com.example.voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

    Button btnLogOut ,v,ca,co;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        v = (Button) findViewById(R.id.button);
        ca =(Button) findViewById(R.id.button2);
        co =(Button) findViewById(R.id.button4);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(), voter.class);
                startActivity(I);
            }
        });
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(), candidate.class);
                startActivity(I);
            }
        });
        co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(), control.class);
                startActivity(I);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(I);

            }
        });
    }
}