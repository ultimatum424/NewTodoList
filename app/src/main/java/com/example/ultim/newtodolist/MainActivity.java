package com.example.ultim.newtodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ultim.newtodolist.DataBase.DatabaseAdapter;
import com.example.ultim.newtodolist.DataBase.TodoTask;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecycleAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), EditActivity.class);
                myIntent.putExtra("ID", 0);
                startActivityForResult(myIntent, 1);
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
      //  adapter.insert(new TodoTask(0, "Заголовок2", "gggggggggggggggg", "21 MAY 2016", 3, 0));
        List<TodoTask> mTasks = adapter.getTodoTasks();
        adapter.close();
        mRecycleAdapter = new RecyclerAdapter(mTasks, this);
        mRecyclerView.setAdapter(mRecycleAdapter);
    }
}
