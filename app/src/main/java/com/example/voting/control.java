package com.example.voting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class control extends AppCompatActivity {

    Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        b1=findViewById(R.id.button);
        b1=findViewById(R.id.button2);
        b1=findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    //voter status true
                System.out.println("1");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    //voter status false
                System.out.println("2");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    //pop result
                System.out.println("3");
            }
        });


    }
}