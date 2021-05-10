package com.example.voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    EditText e1;
    Button btnLogOut;
    ListView listView;
    String v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        v1="-1";
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        listView = (ListView) findViewById(R.id.listview);
        final ArrayList<String> list =new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview, list);
        listView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("candidates")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        list.clear();
                        for ( DataSnapshot snapshot : datasnapshot.getChildren())
                        {
                            mcandidate m =snapshot.getValue(mcandidate.class);
                            String txt="Name: "+m.name+"  "+"Party: "+m.party+" ID:"+m.id;
                            list.add(txt);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                String value1[]=value.split(":");
                System.out.println(value1[value1.length-1]);

                v1=value1[value1.length-1];
                FirebaseDatabase.getInstance().getReference("candidate")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                for ( DataSnapshot snapshot : datasnapshot.getChildren()) {


                                    mcandidate m = snapshot.getValue(mcandidate.class);

                                    if (m.id.equals(v)) {
                                        m.votes += 1;
                                        FirebaseDatabase.getInstance().getReference("candidate").child(m.id)
                                                .setValue(m);
                                        FirebaseDatabase.getInstance().getReference("voters")
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                                                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                                            member m = snapshot.getValue(member.class);
                                                            String id = FirebaseAuth.getInstance().getUid().toString();
                                                            if (id.equals(m.uid)) {
                                                                m.status = "true";
                                                                FirebaseDatabase.getInstance().getReference("voters").child(m.id)
                                                                        .setValue(m);
                                                                break;

                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                        break;
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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