package com.ua.example.kif.kyrsi_17_http_firebase;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Kif on 30.01.2017.
 */

public class RoomViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextViewName;
    public  TextView mTextViewDescription;




    public RoomViewHolder(View itemView) {
        super(itemView);

        mTextViewName = (TextView) itemView.findViewById(android.R.id.text1);

        mTextViewDescription = (TextView) itemView.findViewById(android.R.id.text2);
    }
}
