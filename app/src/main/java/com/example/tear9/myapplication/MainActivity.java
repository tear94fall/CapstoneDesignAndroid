package com.example.tear9.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button reg_button = findViewById(R.id.Register);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView id = (TextView) findViewById(R.id.editText2);
                TextView pw = (TextView) findViewById(R.id.editText);

                String id_str = id.getText().toString();
                String pw_str = pw.getText().toString();

                TextView textView1 = (TextView) findViewById(R.id.textView4);

                // 로그인 성공 여부 확인 하는 함수
                if (!logInCheck(id_str, pw_str)) {
                    /* 로그인 성공시 페이지 전환 */
                    Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    /* 로그인 실패시 현재 페이지 에서 팝업창 띄울것 */
                    /* 일정 횟수 이상 넘어갈 경우에는 블로킹 되도록 처리 함*/
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("로그인 실패")
                            .setMessage("올바르지 않은 시도입니다.")
                            .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌
                }
            }
        });

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

    protected boolean logInCheck(String id, String passwd){
        String send_message = id + passwd;
        return true;
    }
}
