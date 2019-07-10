package com.example.tear9.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class MyInfoActivity extends AppCompatActivity {
    private Context mContext;

    public MyInfoActivity() {
    }

    public MyInfoActivity(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        setTitle("나의 최근 운전 정보");

        Button back_button = findViewById(R.id.back_button);

        ListView drive_info_list_view = findViewById(R.id.drive_info_list_view);
        ArrayList<String> drive_info_array_list = new ArrayList<>();
        this.mContext = getApplicationContext();

        Intent intent = getIntent(); /*데이터 수신*/
        final String user_id = Objects.requireNonNull(intent.getExtras()).getString("user_id"); /*String형*/
        String[] userInfo;

        try {
            userInfo = getUserInfo(user_id);
            String tempUserInfo = userInfo[0];

            String[] userInfoArray = tempUserInfo.split("-");

            String drive_count = userInfoArray[0];
            String sleep_count = userInfoArray[1];
            String alcohol_count = userInfoArray[2];
            String last_drive_date = userInfoArray[3];

            String[] DateArray = last_drive_date.split("/");

            String Year = DateArray[0];
            String Month = DateArray[1];
            String Day = DateArray[2];

            drive_info_array_list.add("운행 횟수 "+drive_count+"회");
            drive_info_array_list.add("졸음 운전 "+sleep_count+"회");
            drive_info_array_list.add("음주 운전 "+alcohol_count+"회");
            drive_info_array_list.add("마지막 운행 "+Year+"년 " + Month+"월 "+ Day+"일");

            DriveInfoListViewAdapter driveInfoListViewAdapter = new DriveInfoListViewAdapter(mContext, drive_info_array_list);

            drive_info_list_view.setAdapter(driveInfoListViewAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected String[] getUserInfo(final String _id) throws InterruptedException {
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