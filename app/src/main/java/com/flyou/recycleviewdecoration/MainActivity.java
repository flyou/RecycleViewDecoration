package com.flyou.recycleviewdecoration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.flyou.recycleviewdecoration.ui.GridDecorationActivity;
import com.flyou.recycleviewdecoration.ui.HeadDecorationActivity;
import com.flyou.recycleviewdecoration.ui.ListDecorationActivity;
import com.flyou.recycleviewdecoration.ui.RankDecorationActivity;
import com.flyou.recycleviewdecoration.ui.SimpleDecorationActivity;
import com.flyou.recycleviewdecoration.ui.StepLineDecorationActivity;
import com.flyou.recycleviewdecoration.ui.StickyHeaderDecorationActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private android.widget.Button simple;
    private android.widget.Button list;
    private android.widget.Button gird;
    private android.widget.Button step;
    private android.widget.Button rank;
    private android.widget.Button header;
    private android.widget.Button stickyHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.stickyHeader = (Button) findViewById(R.id.stickyHeader);
        this.header = (Button) findViewById(R.id.header);
        this.rank = (Button) findViewById(R.id.rank);
        this.step = (Button) findViewById(R.id.step);
        this.gird = (Button) findViewById(R.id.grid);
        this.list = (Button) findViewById(R.id.list);
        this.simple = (Button) findViewById(R.id.simple);
        this.rank.setOnClickListener(this);
        this.step.setOnClickListener(this);
        this.gird.setOnClickListener(this);
        this.list.setOnClickListener(this);
        this.stickyHeader.setOnClickListener(this);
        this.header.setOnClickListener(this);
        this.simple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.simple:
                intent.setClass(this, SimpleDecorationActivity.class);
                break;
            case R.id.list:
                intent.setClass(this, ListDecorationActivity.class);
                break;
            case R.id.grid:
                intent.setClass(this, GridDecorationActivity.class);
                break;
            case R.id.step:
                intent.setClass(this, StepLineDecorationActivity.class);
                break;
            case R.id.rank:
                intent.setClass(this, RankDecorationActivity.class);
                break;
            case R.id.header:
                intent.setClass(this, HeadDecorationActivity.class);
                break;
            case R.id.stickyHeader:
                intent.setClass(this, StickyHeaderDecorationActivity.class);
                break;
        }
        startActivity(intent);
    }
}
