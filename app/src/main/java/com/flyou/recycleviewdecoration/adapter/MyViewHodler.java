package com.flyou.recycleviewdecoration.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flyou.recycleviewdecoration.R;


/**
 * Created by fzl on 2017/08/14.
 * VersionCode: 1
 * Desc:
 */

public class MyViewHodler extends RecyclerView.ViewHolder {
    TextView tv;
    public MyViewHodler(View itemView) {
        super(itemView);
        tv= (TextView) itemView.findViewById(R.id.title);
    }
}
