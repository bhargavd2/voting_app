package com.example.voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class candidate extends AppCompatActivity {
    Button v,ca,co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);
        v = (Button) findViewById(R.id.button);
        ca =(Button) findViewById(R.id.button2);
        co =(Button) findViewById(R.id.button4);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(), AddCandidate.class);
                startActivity(I);
            }
        });
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pop
            }
        });
        co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(), viewcandidate.class);
                startActivity(I);
            }
        });
    }
}