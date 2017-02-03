package com.ua.example.kif.kyrsi_17_http_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private EditText mEditTextDescription;
    private RecyclerView mListView;

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Room, RoomViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextDescription=(EditText) findViewById(R.id.editTextDescription);
        mListView = (RecyclerView) findViewById(R.id.listView);

        mListView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("/rooms/"+mEditTextName.getText().toString()+"/")
                        .setValue(new Room(mEditTextDescription.getText().toString()));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        mAdapter = new FirebaseRecyclerAdapter<Room, RoomViewHolder>(
                Room.class,
                android.R.layout.simple_list_item_2,
                RoomViewHolder.class,
                mDatabase.child("/rooms")){

            @Override
            protected void populateViewHolder(RoomViewHolder viewHolder, Room model, int position) {
                final String key = mAdapter.getRef(position).getKey();

                viewHolder.mTextViewName.setText(key);
                viewHolder.mTextViewDescription.setText(model.getDescription());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra(Main2Activity.EXTRA_ROOM_ID, key);

                        startActivity(intent);
                    }
                });
            }
        };

        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAdapter !=null){
            mAdapter.cleanup();
        }

    }
}
