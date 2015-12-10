package com.example.administrator.onezw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
   private TextView notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notification= (TextView) this.findViewById(R.id.notification);
        Intent intent=getIntent();
        String data=intent.getStringExtra("data");
        notification.setText(data);

    }

}
