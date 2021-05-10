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
    String us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        v1="-1";
        us=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
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
                v1=value1[value1.length-1];
                FirebaseDatabase.getInstance().getReference("candidates")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                for ( DataSnapshot snapshot : datasnapshot.getChildren()) {

                                    mcandidate m = snapshot.getValue(mcandidate.class);

                                    if (m.id.equals(v1)) {

                                        FirebaseDatabase.getInstance().getReference("voters")
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                                                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                                            member m1 = snapshot.getValue(member.class);

                                                            if (us.equals(m1.uid)&& "false".equals(m1.status)) {
                                                                m1.status = "true";
                                                                m.votes = m.votes+1;
                                                                FirebaseDatabase.getInstance().getReference("candidates").child(m.id)
                                                                        .setValue(m);
                                                                FirebaseDatabase.getInstance().getReference("voters").child(m1.id)
                                                                        .setValue(m1);
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
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(getApplicationContext(), MainActivity.class);
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