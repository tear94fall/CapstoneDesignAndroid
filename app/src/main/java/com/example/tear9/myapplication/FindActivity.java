package com.example.tear9.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class FindActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        // 상단 타이틀 변경
        setTitle("회원 정보 찾기");
        backPressCloseHandler = new BackPressCloseHandler(this);

        final TextView find_for_id_name = (TextView) findViewById(R.id.find_id_for_name);
        final TextView find_for_id_tel = (TextView) findViewById(R.id.find_id_for_tel);
        Button find_id_button = findViewById(R.id.find_id_button);

        final TextView find_for_pw_name = (TextView) findViewById(R.id.find_pw_for_id);
        final TextView find_for_pw_tel = (TextView) findViewById(R.id.find_pw_for_name);
        Button find_pw_button = findViewById(R.id.find_pw_button);

        find_id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_find_for_id_name = find_for_id_name.getText().toString();
                String str_find_for_id_tel = find_for_id_tel.getText().toString();

                try {
                    String[] result = FindUserId(str_find_for_id_name, str_find_for_id_tel);
                    if ("false".equals(result[0])) {
                        Toast.makeText(getApplicationContext(), "가입된 정보가 없습니다.", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "고객님의 아이디는 " + result[0] + " 입니다.", Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        find_pw_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_find_for_pw_id = find_for_pw_name.getText().toString();
                String str_find_for_pw_name = find_for_pw_tel.getText().toString();

                try {
                    String[] result = FindUserPw(str_find_for_pw_id, str_find_for_pw_name);
                    if ("false".equals(result[0])) {
                        Toast.makeText(getApplicationContext(), "가입된 정보가 없습니다.", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "고객님의 비밀번호는 " + result[0] + " 입니다.", Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected String[] FindUserId(final String _name, final String _tel) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.FindUserId(_name, _tel);
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

    protected String[] FindUserPw(final String _id, final String _name) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.FindUserPw(_id, _name);
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
