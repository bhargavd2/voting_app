package com.example.voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewvoter extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewvoter);

        listView = (ListView) findViewById(R.id.listview);
        final ArrayList<String> list =new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview, list);
        listView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("voters")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        list.clear();
                        for ( DataSnapshot snapshot : datasnapshot.getChildren())
                        {
                            member m =snapshot.getValue(member.class);
                            String txt ="Id:"+m.id+" Email:"+m.email;
                            list.add(txt);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}