package com.example.td1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private EditText editText;
    private Button btn;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        editText =  findViewById(R.id.editTextSearch);
        btn =  findViewById(R.id.buttonSearch);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                controller.onClickFilter(text);
            }
        });


        controller = new Controller(this);
        controller.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showList(List<Breaches> breachesList) {
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final List<Breaches> breaches = new ArrayList<>();
        for (int i = 0; i < breachesList.size(); i++) {
            breaches.add(breachesList.get(i));
        }// define an adapter
        breaches.sort(Comparator.comparingInt(Breaches::getPwnCount).reversed());
        mAdapter = new MyAdapter(breaches, getBaseContext(), getListener());
        recyclerView.setAdapter(mAdapter);
    }

    private MyAdapter.OnItemClickListener getListener() {
        return new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Breaches item) {
                controller.onItemClick(item);
            }
        };
    }

    public void navigateToDetail(String json) {
        //remove(position);
        Intent breachIntent = new Intent(this, BreachActivity.class);
        breachIntent.putExtra(Constants.currentBreachIntentKey, json);
        startActivity(breachIntent);
    }



}
