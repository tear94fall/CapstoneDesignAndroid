package com.example.tear9.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class MyInfoActivity extends AppCompatActivity {
    @Override public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        TextView Drive_cnt = (TextView) findViewById(R.id.DriveCnt);
        TextView Sleep_cnt = (TextView) findViewById(R.id.SleepCnt);
        TextView Drunk_cnt = (TextView) findViewById(R.id.DrunkCnt);
        TextView LastDrive_date = (TextView) findViewById(R.id.LastDriveDate);

        Intent intent = getIntent(); /*데이터 수신*/
        final String user_id = intent.getExtras().getString("user_id"); /*String형*/
        String[] userInfo = new String[1];

        try {
            userInfo = getUserInfo(user_id);
            String tempUserInfo = userInfo[0];

            String[] userInfoArray = tempUserInfo.split("-");

            String drive_count = userInfoArray[0];
            String sleep_count = userInfoArray[1];
            String alcohol_count = userInfoArray[2];
            String last_drive_date = userInfoArray[3];

            Drive_cnt.setText("총 "+drive_count+"회 운전하셨습니다.");
            Sleep_cnt.setText("총 "+sleep_count+"회 졸음이 감지 되었습니다.");
            Drunk_cnt.setText("총 "+alcohol_count+"회 음주 운전 시도가 있었습니다.");

            String[] DateArray = last_drive_date.split("/");

            String Year = DateArray[0];
            String Month = DateArray[1];
            String Day = DateArray[2];

            LastDrive_date.setText("20"+Year+"년 " + Month+"월 "+ Day+"일");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected String[] getUserInfo(final String _id) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.getUserInfo(_id);
                    result[0] = client.getResponse_data();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        sleep(1000);
        return result;
    }
}