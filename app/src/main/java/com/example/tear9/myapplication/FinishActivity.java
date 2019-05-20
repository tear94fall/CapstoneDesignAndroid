package com.example.tear9.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Button button = findViewById(R.id.button2);

        new AlertDialog.Builder(FinishActivity.this)
                .setTitle("로그인 성공!!")
                .setMessage("환영합니다.")
                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                    }
                }).show(); // 팝업창 보여줌

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
    }
}
