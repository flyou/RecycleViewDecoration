package com.flyou.recycleviewdecoration.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flyou.library.decoration.DividerGridItemDecoration;
import com.flyou.recycleviewdecoration.R;
import com.flyou.recycleviewdecoration.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GridDecorationActivity extends AppCompatActivity {
    private RecyclerView recycleView;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);
        for (int i = 1; i < 80; i++) {

            dataList.add("第" + i + "个");
        }
        this.recycleView = (RecyclerView) findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new GridLayoutManager(this,3));
        DividerGridItemDecoration headerDecoration = new DividerGridItemDecoration(this);
        recycleView.addItemDecoration(headerDecoration);
        recycleView.setAdapter(new RecyclerAdapter(this, dataList));
    }
}
