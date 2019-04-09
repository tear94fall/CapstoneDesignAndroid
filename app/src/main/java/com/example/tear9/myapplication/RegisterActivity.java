package com.example.tear9.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    boolean ID_checker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button Register_request_button = findViewById(R.id.Register_Request);
        Button ID_check = findViewById(R.id.ID_Check);

        // 아이디 중복 검사를 위한 변수
        ID_checker = false;

        Register_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView input_id = (TextView) findViewById(R.id.UserIDInput);
                TextView input_pw = (TextView) findViewById(R.id.UserPWInput);

                String id = input_id.getText().toString();
                String pw = input_pw.getText().toString();

                /*
                회원 가입이 안되야 하는 경우

                1. 중복 아이디 존재 ( 아이디 중복을 체크하는 버튼 추가할 것)
                2. 아이디 빈값
                3. 비밀번호 빈값
                 */

                // 아이디가 빈값일 경우
                if(id.trim().length() == 0){
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("아이디를 입력해주세요")
                            .setMessage("올바른 아이디를 입력해주세요")
                            .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌

                    // 패스워드가 빈값을 경우
                }else if(pw.trim().length() == 0){
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("비밀번호를 입력해주세요")
                            .setMessage("올바른 비밀번호를 입력해주세요")
                            .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌
                }

                if(ID_checker){
                    // 서버에 값을 보내는 로직 추가할것
                }
            }
        });

        ID_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView input_id = (TextView) findViewById(R.id.UserIDInput);

                // 중복 아이디가 존재 하지 않을 경우 ID_checker 변수를 True로 만듦

                // 아이디 중복검사를 위한 로직 추가 할것
                if(true/* 아이디 중복 검사하는 로직*/){
                    // 중복 아이디가 존재 하지 않을때
                    ID_checker = true;

                    // 중복 아이디가 없으면 그대로 로직 수행
                }else{
                    // 중복 아이디 존재
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("이미 등록된 아이디 입니다.")
                            .setMessage("새로운 아이디를 입력해주세요")
                            .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌
                }
            }
        });
    }
}
