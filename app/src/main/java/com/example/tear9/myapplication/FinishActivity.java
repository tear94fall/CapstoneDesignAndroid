package com.example.tear9.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Button button = findViewById(R.id.button2);
        Button mypage_button = findViewById(R.id.mypage_button);
        Button modify_button = findViewById(R.id.modify_button);

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
            new AlertDialog.Builder(FinishActivity.this)
                    .setTitle("로그인 성공!!")
                    .setMessage("마지막 운행으로 부터 " + result[0] + "일이 지났습니다.")
                    .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                        }
                    }).show(); // 팝업창 보여줌
        }else{
            new AlertDialog.Builder(FinishActivity.this)
                    .setTitle("로그인 성공!!")
                    .setMessage("환영합니다.")
                    .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                        }
                    }).show(); // 팝업창 보여줌
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number;
                number=(int)(Math.random()*100);
                number%=2;

                if(number==0) {
                    Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), Captcah2Activity.class);
                    startActivity(intent);
                    finish();
                }
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
