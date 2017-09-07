package com.flyou.recycleviewdecoration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyou.recycleviewdecoration.R;

import java.util.List;

/**
 * Created by fzl on 2017/08/14.
 * VersionCode: 1
 * Desc:
 */

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHodler> {
    private Context context;
    private List<String> dataList;
    public RecyclerAdapter(Context context, List<String> dataList) {
        this.dataList=dataList;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycle_line_item, null);
        MyViewHodler myViewHodler=new MyViewHodler(itemView);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodler holder, int position) {

        holder.tv.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
