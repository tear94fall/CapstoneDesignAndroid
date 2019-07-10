package com.example.tear9.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class MenuActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 상단 타이틀 변경
        setTitle("메뉴들");
        backPressCloseHandler = new BackPressCloseHandler(this);

        Button button1 = findViewById(R.id.button2);
        Button button2 = findViewById(R.id.button6);
        Button mypage_button = findViewById(R.id.mypage_button);
        Button modify_button = findViewById(R.id.modify_button);
        Button my_location_button = findViewById(R.id.my_location_button);

        Intent intent = getIntent(); /*데이터 수신*/
        final String user_id = intent.getExtras().getString("user_id"); /*String형*/

        String[] result = new String[1];
        try {
            result = lastDriverDate(user_id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (result[0].length() != 0 || result[0] != "0") {
            Toast.makeText(getApplicationContext(), "로그인에 성공하셨습니다. \n마지막 운행으로 부터 " + result[0] + "일이 지났습니다.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "로그인 성공!!", Toast.LENGTH_LONG).show();
        }

        // 테스트 1 시작
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                intent.putExtra("user_id", user_id); /*송신*/
                startActivity(intent);
            }
        });

        // 테스트 2 시작
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Captcah2Activity.class);
                intent.putExtra("user_id", user_id); /*송신*/
                startActivity(intent);
            }
        });

        // 운전 정보 액티비티
        mypage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                intent.putExtra("user_id", user_id); /*송신*/
                startActivity(intent);
            }
        });

        // 개인정보 변경 액티비티
        modify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("user_id", user_id); /*송신*/
                startActivity(intent);
            }
        });

        // 구글맵에 내 위치 표시하기
        my_location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                //startActivity(intent);
            }
        });
    }

    // 서버에게 보내는 마지막 운전 날짜 요청 메소드
    protected String[] lastDriverDate(final String _id) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.getLastDriveDate(_id);
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
