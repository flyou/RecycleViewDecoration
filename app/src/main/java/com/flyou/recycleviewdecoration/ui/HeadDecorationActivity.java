package com.flyou.recycleviewdecoration.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flyou.library.bean.StickyHeadeBean;
import com.flyou.library.decoration.HeaderDecoration;
import com.flyou.recycleviewdecoration.R;
import com.flyou.recycleviewdecoration.adapter.StickyHeaderRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HeadDecorationActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    private List<StickyHeadeBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);
        for (int i = 1; i < 80; i++) {
            StickyHeadeBean stickyHeadeBean=new StickyHeadeBean("第" + i + "个", false, false,false);
            if (i == 1) {
                stickyHeadeBean.setFirst(true);
                stickyHeadeBean.setTeamFirst(true);
            } else if ((i-1) % 4 == 0) {

                stickyHeadeBean.setTeamFirst(true);

            } else if (i % 4 == 0) {
                stickyHeadeBean.setTeamLast(true);
            } else {

            }
            dataList.add(stickyHeadeBean);
        }
        this.recycleView = (RecyclerView) findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        HeaderDecoration headerDecoration = new HeaderDecoration(this);
        recycleView.addItemDecoration(headerDecoration);
        recycleView.setAdapter(new StickyHeaderRecyclerAdapter(this, dataList));
    }
}
