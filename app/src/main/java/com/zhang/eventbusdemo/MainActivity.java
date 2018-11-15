package com.zhang.eventbusdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button btnNext;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        btnNext = (Button) findViewById(R.id.btn_next);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Object event) {
        if (event instanceof MessageEvent) {
            MessageEvent e = (MessageEvent) event;
            Log.e("MainActivity", "收到：" + e.getMessage());
            tv_show.setText(e.getMessage());
        } else if (event instanceof Integer) {
            Log.e("MainActivity", "收到1：" + event);
        } else if (event instanceof String) {
            Log.e("MainActivity", "收到2：" + event);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
