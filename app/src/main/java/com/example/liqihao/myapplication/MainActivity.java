package com.example.liqihao.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
//    @BindView(R.id.toolbar)
//    android.support.v7.widget.Toolbar toolbar;

    Activity mContext;

    DrawerLayout mDrawerLayout;

    NavigationView navView;

    FloatingActionButton fab;

    Button btn1;

    private SwipeRefreshLayout swipeRefreshLayout;

    public int T = 60;
    private Timer mTimer;
    private TimerTask mTask;
    private long duration = 10000;
    private long temp_duration;
    private String clickBeffor = "点击发送";
    private String clickAfter = "秒后重新开始";

    private Fruit[] fruits = {new Fruit("head",R.drawable.head),
    new Fruit("gudago",R.drawable.aa123)};

    private List<Fruit> fruitList = new ArrayList<>();

    private FruitAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar);
        navView = (NavigationView) findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        mContext = MainActivity.this;

        initFruits();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
//        btn1 = (Button) findViewById(R.id.btn_1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "dsadasdas", Toast.LENGTH_SHORT).show();
//                startTimer();
//            }
//        });


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.result_fa);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_call:
                        Toast.makeText(mContext, "This is nav_call", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_tongbu:
                        Toast.makeText(mContext, "This is tongbu", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.delete:
                        Toast.makeText(mContext, "This is delete", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.shoucang:
                        Toast.makeText(mContext, "This is shoucang", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"停止倒数", Snackbar.LENGTH_SHORT)
                        .setAction("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (recyclerView.isActivated()){
                                    Toast.makeText(MainActivity.this, "还未开始", Toast.LENGTH_SHORT).show();
                                }else{
//                                    stopTimer();
//                                    btn1.setEnabled(true);
//                                    btn1.setText(clickBeffor);
                                    Toast.makeText(MainActivity.this, "停止成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
        });

    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception E){
                    E.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for(int i = 0;i < 50;i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.upload:
                Intent intent = new Intent(mContext,map.class);
                startActivity(intent);
                break;
            case R.id.setup:
                Toast.makeText(mContext, "this is setup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.many:
                Toast.makeText(mContext, "this is many", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg) {
            btn1.setText(temp_duration/1000 + clickAfter);
            temp_duration -= 1000;
            if (temp_duration < 0) {
                btn1.setEnabled(true);
                btn1.setText(clickBeffor);
                stopTimer();
            }
        }
    };



//    @OnClick({R.id.btn_1})
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_1:
//                btn1.setEnabled(false);
//                break;
//        }
//    }

    private void startTimer(){
        temp_duration = duration;
        btn1.setEnabled(false);
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0x01);
            }
        };
        mTimer.schedule(mTask,0,1000);
    }
    private void stopTimer() {
        mTask.cancel();
    }
//    private class ButtonListener implements View.OnClickListener{
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.btn_1:
//                    startTimer();
//                    Toast.makeText(mContext, "dasdasdsa", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
