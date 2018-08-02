package com.bonnidee.rxjava2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onStandradActivity(View view) {
        startActivity(new Intent(this, StandardActivity.class));

    }

    public void thread(View view) {
        startActivity(new Intent(this, ThreadActivity.class));
    }
}
