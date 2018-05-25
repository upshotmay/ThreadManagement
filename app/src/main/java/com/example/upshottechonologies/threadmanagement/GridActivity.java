package com.example.upshottechonologies.threadmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class GridActivity extends AppCompatActivity {

    GridView gridView;
    GridAdpater adapter;

    int []img = {R.drawable.a, R.drawable.b, R.drawable.c,R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.a, R.drawable.b, R.drawable.c};
    String text[] = {"Image", "India", "Bird","Image", "India", "Bird","Image", "India", "Bird"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        gridView = findViewById(R.id.gridView);
        adapter = new GridAdpater(img, text, this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
