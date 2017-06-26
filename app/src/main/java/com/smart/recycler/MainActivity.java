package com.smart.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_base_use).setOnClickListener(this);
        findViewById(R.id.btn_pull_up_down).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_base_use:
                startActivity(new Intent(this, BaseUseActivity.class));
                break;
            case R.id.btn_pull_up_down:
                startActivity(new Intent(this, PullUpDownActivity.class));
                break;
            default:
                break;
        }

    }
}
