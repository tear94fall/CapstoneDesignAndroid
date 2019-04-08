package com.example.tear9.myapplication;

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

                if (logInCheck(id_str, pw_str) == true) {
                    /* 로그인 성공시 페이지 전환 */
                    Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    /* 로그인 실패시 현재 페이지 에서 팝업창 띄울것 */
                    /* 일정 횟수 이상 넘어갈 경우에는 블로킹 되도록 처리 함*/
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
        return true;
    }
}
