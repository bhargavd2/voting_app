package com.example.voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    EditText e1;
    EditText e2;
    Button b;
    String status;

    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        e1=findViewById(R.id.email);
        e2=findViewById(R.id.password);
        b=findViewById(R.id.login);
        status="false";
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String emailID = e1.getText().toString();
                final String paswd = e2.getText().toString();
                if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (paswd.isEmpty()) {
                    e2.setError("Enter Password!");
                    e2.requestFocus();
                } else if (emailID.isEmpty()) {
                    e1.setError("Provide your Email first!");
                    e1.requestFocus();
                } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(emailID, paswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Not sucessfull", Toast.LENGTH_SHORT).show();
                            } else {
                                if(emailID.equals("admin@test.com")) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                                    finish();
                                    return;
                                }
                                else
                                {

                                    FirebaseDatabase.getInstance().getReference("election").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            status=snapshot.getValue().toString();
                                            if(status.equals("true"))
                                            {
                                                startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                                                finish();
                                                return;
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "election not started", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}