package com.example.tear9.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class TestPassActivity extends AppCompatActivity {
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM월dd일 HH시mm분");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);

    TextView dateNow;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpass);

        Intent intent = getIntent(); /*데이터 수신*/
        final String user_id = intent.getExtras().getString("user_id"); /*String형*/

        TextView dateNow = (TextView) findViewById(R.id.textView5);
        dateNow.setText(formatDate);    // TextView 에 현재 시간 문자열 할당

        try {
            UpdateDriveDate(user_id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
