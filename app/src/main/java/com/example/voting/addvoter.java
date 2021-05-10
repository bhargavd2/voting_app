package com.example.voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

public class addvoter extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b;
    FirebaseAuth fauth;
    FirebaseDatabase ab;
    String id,email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvoter);

        e1=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        e3=findViewById(R.id.editText3);
        fauth=FirebaseAuth.getInstance();
        b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email=e2.getText().toString().trim();
                pass=e3.getText().toString().trim();
                id=e1.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    e1.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    e2.setError("Password is required.");
                    return;
                }
                if(TextUtils.isEmpty(id)){
                    e2.setError("id is required.");
                    return;
                }
                if(pass.length()<6){
                    e2.setError("Password must be more than 6 characters.");
                    return;
                }


                fauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            member m=new member(FirebaseAuth.getInstance().getCurrentUser().getEmail(),"false",id,FirebaseAuth.getInstance().getCurrentUser().getUid());
                            FirebaseDatabase.getInstance().getReference("voters").child(id)
                                    .setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(addvoter.this,"User Created",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(addvoter.this,"User Created not Successful",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            startActivity(new Intent(getApplicationContext(),addvoter.class));
                            finish();
                            return;
                        }
                        else{
                            Toast.makeText(addvoter.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


}
class member
{
    public String status;
    public String email;
    public String id;
    public String uid;

    member()
    {}

    member(String a,String b,String C,String d)
    {
        status=b;
        email=a;
        id=C;
        uid=d;
    }
}