package com.example.voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class control extends AppCompatActivity {

    Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String status="true";
                FirebaseDatabase.getInstance().getReference("election").setValue(status)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(control.this,"Started",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(control.this,"Start not Successful",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                FirebaseDatabase.getInstance().getReference("voters")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                                for ( DataSnapshot snapshot : datasnapshot.getChildren())
                                {
                                    member m =snapshot.getValue(member.class);
                                    member m1=new member(m.email,"false",m.id,m.uid);
                                    FirebaseDatabase.getInstance().getReference("voters").child(m.id)
                                            .setValue(m1);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String status="false";
                FirebaseDatabase.getInstance().getReference("election").setValue(status)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(control.this,"Stop Successful",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(control.this,"Stop not Successful",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent I = new Intent(getApplicationContext(), viewresults.class);
                startActivity(I);
            }
        });


    }
}