package com.example.voting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCandidate extends AppCompatActivity {

    EditText e1,e2,e3;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate);
        e1=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        e3=findViewById(R.id.editText3);
        b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // add candidate
            }
        });

    }
}