package com.example.tear9.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button reg_button = findViewById(R.id.Register);

        // 로그인 버튼 눌렀을 경우
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView id = (TextView) findViewById(R.id.editText2);
                TextView pw = (TextView) findViewById(R.id.editText);

                String id_str = id.getText().toString();
                String pw_str = pw.getText().toString();

                TextView textView1 = (TextView) findViewById(R.id.textView4);

                // 로그인 성공 여부 확인 하는 함수
                try {
                    String result[] = logInCheck(id_str, pw_str);
                    boolean checker = false;

                    if("true".equals(result[0])) {
                        checker = true;
                    }

                    if(checker) {
                        /* 로그인 성공시 페이지 전환 */
                        Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        /* 로그인 실패시 현재 페이지 에서 팝업창 띄울것 */
                        /* 일정 횟수 이상 넘어갈 경우에는 블로킹 되도록 처리 함*/
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("로그인 실패")
                                .setMessage("아디이와 비밀번호를 확인 해주세요")
                                .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    }
                                }).show(); // 팝업창 보여줌
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 회원가입 버튼이 눌렸을 경우
        reg_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /* 회원가입 버튼이 눌렸을 경우에는 무조건 회원 가입 페이지로 화면 전환 */
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected String[] logInCheck(final String _id, final String _passwd) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.setLoginInfo(_id, _passwd);
                    client.loginCheck();
                    result[0] = client.getResponse_data();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        Thread.sleep(1000);
        return result;
    }
}