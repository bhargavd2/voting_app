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
public class AddCandidate extends AppCompatActivity {

    EditText e1,e2,e3;
    Button b;
    String name,id,party;
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
                name=e1.getText().toString().trim();
                id=e2.getText().toString().trim();
                party=e3.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    e1.setError("name is required.");
                    return;
                }
                if(TextUtils.isEmpty(id)){
                    e2.setError("id is required.");
                    return;
                }
                if(TextUtils.isEmpty(party)){
                    e2.setError("party is required.");
                    return;
                }
                mcandidate c=new mcandidate(name,party,id);
                FirebaseDatabase.getInstance().getReference("candidates").child(id)
                        .setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(AddCandidate.this,"candidate Created",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AddCandidate.this,"candidate Created not Successful",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }
}
class mcandidate
{
    public String name;
    public String party;
    public int votes;
    public String id;

    mcandidate()
    {    }

    mcandidate(String a,String b,String c)
    {
        name=a;
        party=b;
        votes=0;
        id=c;
    }
}