package com.ua.example.kif.kyrsi_17_http_firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    public static final String EXTRA_ROOM_ID = "com.ua.example.kif.kyrsi_17_http_firebase.extra.room_ID";

    private EditText mEditTextMessage;
    private RecyclerView mListView;

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Message, RoomViewHolder> mAdapter;

    private String mRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mRoomId = getIntent().getStringExtra(EXTRA_ROOM_ID);

        mEditTextMessage=(EditText) findViewById(R.id.editTextMessage);
        mListView = (RecyclerView) findViewById(R.id.listView2);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        mListView.setLayoutManager(layoutManager);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> map = new HashMap<>();
                map.put("message", mEditTextMessage.getText().toString());
                map.put("time", ServerValue.TIMESTAMP);

                mDatabase.child("messages/" + "/" + mRoomId).push().setValue(map);

                mEditTextMessage.setText(null);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        mAdapter = new FirebaseRecyclerAdapter<Message, RoomViewHolder>(
                Message.class,
                android.R.layout.simple_list_item_2,
                RoomViewHolder.class,
                mDatabase.child("/messages/"+mRoomId).orderByChild("time")){

            @Override
            protected void populateViewHolder(RoomViewHolder viewHolder, Message model, int position) {

                String time = model.getTime() > 0 ? DateUtils.getRelativeTimeSpanString(
                        model.getTime(),
                        System.currentTimeMillis(),
                        DateUtils.MINUTE_IN_MILLIS).toString() : "";

                viewHolder.mTextViewName.setText(model.getMessage());
                viewHolder.mTextViewDescription.setText(time);
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
