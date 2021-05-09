package com.example.voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class removevoter extends AppCompatActivity {

    EditText e1;
    Button b;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removevoter);
        e1 = findViewById(R.id.editText);
        b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = e1.getText().toString().trim();
                if (TextUtils.isEmpty(id)) {
                    e1.setError("id is required.");
                    return;
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("voters")
                        .child(id)
                        .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(removevoter.this,"voter removed",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(removevoter.this,"enter valid id",Toast.LENGTH_SHORT).show();
                        }

                    }
                });            }
        });
    }
}

