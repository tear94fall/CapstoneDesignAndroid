package com.example.tear9.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class TestPassActivity extends AppCompatActivity {
    private AsyncTask<Void, Void, Void> mTask;
    TextView current_time;
    TextView realtime_clock;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpass);

        setTitle("운행 시작");
        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent intent = getIntent(); /*데이터 수신*/
        final String user_id = intent.getExtras().getString("user_id"); /*String형*/

        current_time = (TextView) findViewById(R.id.drive_start_time_text_view);
        realtime_clock = (TextView) findViewById(R.id.realtime_clock);

        String temp = DateFormat.getDateTimeInstance().format(new Date());
        current_time.setText(temp);

        ShowTimeMethod();

        try {
            UpdateDriveDate(user_id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ShowTimeMethod()
    {
        mTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                while (true)
                {
                    try
                    {
                        publishProgress();
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            protected void onProgressUpdate(Void... progress)
            {
                String temp = DateFormat.getDateTimeInstance().format(new Date());
                realtime_clock.setText(temp);
            }
        };
        mTask.execute();
    }

    protected void UpdateDriveDate(final String _id) throws IOException, InterruptedException {
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.UpdateLastDriveDate(_id);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        sleep(1000);
    }
}