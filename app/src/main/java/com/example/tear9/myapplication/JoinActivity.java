package com.example.tear9.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Thread.sleep;

public class JoinActivity extends AppCompatActivity {
    /*
    정답을 배열로 고정헀지만, 서버와 통신하여 값을 비교하는 로직 구상할것
     */

    boolean test_start_checker = false;

    String[] result = new String[1];

    String captcha_answer;
    String[] captcha_answer_arr;


    HashMap<Integer, Boolean> answer_map = new HashMap<Integer, Boolean>();
    HashMap<Integer, Boolean> test_map = new HashMap<Integer, Boolean>();

    public JoinActivity() {
    }

    // 이미지 버튼
    ImageButton recaptcha_Image1;
    ImageButton recaptcha_Image2;
    ImageButton recaptcha_Image3;
    ImageButton recaptcha_Image4;
    ImageButton recaptcha_Image5;
    ImageButton recaptcha_Image6;
    ImageButton recaptcha_Image7;
    ImageButton recaptcha_Image8;
    ImageButton recaptcha_Image9;

    // 버튼 눌렸는지 체크
    boolean Button1Click = false;
    boolean Button2Click = false;
    boolean Button3Click = false;
    boolean Button4Click = false;
    boolean Button5Click = false;
    boolean Button6Click = false;
    boolean Button7Click = false;
    boolean Button8Click = false;
    boolean Button9Click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        recaptcha_Image1 = (ImageButton) findViewById(R.id.imageButton);
        recaptcha_Image2 = (ImageButton) findViewById(R.id.imageButton2);
        recaptcha_Image3 = (ImageButton) findViewById(R.id.imageButton3);
        recaptcha_Image4 = (ImageButton) findViewById(R.id.imageButton4);
        recaptcha_Image5 = (ImageButton) findViewById(R.id.imageButton5);
        recaptcha_Image6 = (ImageButton) findViewById(R.id.imageButton6);
        recaptcha_Image7 = (ImageButton) findViewById(R.id.imageButton7);
        recaptcha_Image8 = (ImageButton) findViewById(R.id.imageButton8);
        recaptcha_Image9 = (ImageButton) findViewById(R.id.imageButton9);

        Button button_reset = findViewById(R.id.reset);


        // 제출 버튼 선언
        Button button_submit = findViewById(R.id.submit);
        Button test_start = findViewById(R.id.button3);

        /*
        * 테스트 넘버 랜덤 설정 하는 함수
        */

        // 캡차 1번 이미지
        recaptcha_Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button1Click) {
                    recaptcha_Image1.setSelected(true);
                    recaptcha_Image1.setPressed(true);
                    Button1Click = true;
                } else {
                    recaptcha_Image1.setSelected(false);
                    recaptcha_Image1.setPressed(false);
                    Button1Click = false;
                }
            }
        });

        recaptcha_Image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button2Click) {
                    recaptcha_Image2.setSelected(true);
                    recaptcha_Image2.setPressed(true);
                    Button2Click = true;
                } else {
                    recaptcha_Image2.setSelected(false);
                    recaptcha_Image2.setPressed(false);
                    Button2Click = false;
                }
            }
        });
        recaptcha_Image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button3Click) {
                    recaptcha_Image3.setSelected(true);
                    recaptcha_Image3.setPressed(true);
                    Button3Click = true;
                } else {
                    recaptcha_Image3.setSelected(false);
                    recaptcha_Image3.setPressed(false);
                    Button3Click = false;
                }
            }
        });
        recaptcha_Image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button4Click) {
                    recaptcha_Image4.setSelected(true);
                    recaptcha_Image4.setPressed(true);
                    Button4Click = true;
                } else {
                    recaptcha_Image4.setSelected(false);
                    recaptcha_Image4.setPressed(false);
                    Button4Click = false;
                }
            }
        });
        recaptcha_Image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button5Click) {
                    recaptcha_Image5.setSelected(true);
                    recaptcha_Image5.setPressed(true);
                    Button5Click = true;
                } else {
                    recaptcha_Image5.setSelected(false);
                    recaptcha_Image5.setPressed(false);
                    Button5Click = false;
                }
            }
        });
        recaptcha_Image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button6Click) {
                    recaptcha_Image6.setSelected(true);
                    recaptcha_Image6.setPressed(true);
                    Button6Click = true;
                } else {
                    recaptcha_Image6.setSelected(false);
                    recaptcha_Image6.setPressed(false);
                    Button6Click = false;
                }
            }
        });
        recaptcha_Image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button7Click) {
                    recaptcha_Image7.setSelected(true);
                    recaptcha_Image7.setPressed(true);
                    Button7Click = true;
                } else {
                    recaptcha_Image7.setSelected(false);
                    recaptcha_Image7.setPressed(false);
                    Button7Click = false;
                }
            }
        });
        recaptcha_Image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button8Click) {
                    recaptcha_Image8.setSelected(true);
                    recaptcha_Image8.setPressed(true);
                    Button8Click = true;
                } else {
                    recaptcha_Image8.setSelected(false);
                    recaptcha_Image8.setPressed(false);
                    Button8Click = false;
                }
            }
        });
        recaptcha_Image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button9Click) {
                    recaptcha_Image9.setSelected(true);
                    recaptcha_Image9.setPressed(true);
                    Button9Click = true;
                } else {
                    recaptcha_Image9.setSelected(false);
                    recaptcha_Image9.setPressed(false);
                    Button9Click = false;
                }
            }
        });


        // 초기화 버튼 클릭 이벤트
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recaptcha_Image1.isSelected()) {
                    recaptcha_Image1.setSelected(false);
                    Button1Click = false;
                }
                if(recaptcha_Image2.isSelected()) {
                    recaptcha_Image2.setSelected(false);
                    Button2Click = false;
                }
                if(recaptcha_Image3.isSelected()) {
                    recaptcha_Image3.setSelected(false);
                    Button3Click = false;
                }
                if(recaptcha_Image4.isSelected()) {
                    recaptcha_Image4.setSelected(false);
                    Button4Click = false;
                }
                if(recaptcha_Image5.isSelected()) {
                    recaptcha_Image5.setSelected(false);
                    Button5Click = false;
                }
                if(recaptcha_Image6.isSelected()) {
                    recaptcha_Image6.setSelected(false);
                    Button6Click = false;
                }
                if(recaptcha_Image7.isSelected()) {
                    recaptcha_Image7.setSelected(false);
                    Button7Click = false;
                }
                if(recaptcha_Image8.isSelected()) {
                    recaptcha_Image8.setSelected(false);
                    Button8Click = false;
                }
                if(recaptcha_Image9.isSelected()) {
                    recaptcha_Image9.setSelected(false);
                    Button9Click = false;
                }
            }
        });

        // 제출 버튼 클릭 이벤트
        /*
        * 서버에서 문제를 받아옴 // get_test_set
        * 서버에 해답을 보냄
        * 서버에 결과를 받아옴
        * 결과를 처리
        * */

        test_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test_start_checker = true;
                try {
                    result = GetTestSet();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                captcha_answer = result[0];
                captcha_answer_arr = captcha_answer.split("-");

                // 정답 초기화 (테스트 시작 버튼 누를때마다 정답 갱신)
                if(!answer_map.isEmpty()) {
                    answer_map.clear();
                }

                for(int i=0;i<10;i++){
                    answer_map.put(i, false);
                }

                for(String idx : captcha_answer_arr){
                    int __idx = Integer.parseInt(idx);
                    answer_map.put(__idx, true);
                }

                new AlertDialog.Builder(JoinActivity.this)
                        .setTitle("테스트 조건입니다.")
                        .setMessage(captcha_answer+"해당 숫자를 눌러주세요")
                        .setNeutralButton("시작하기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }
                        }).show(); // 팝업창 보여줌
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!test_start_checker){
                    new AlertDialog.Builder(JoinActivity.this)
                            .setTitle("테스트를 시작할수 없습니다.")
                            .setMessage("테스트 시작 버튼을 눌러 주세요.")
                            .setNeutralButton("테스트 시작 버튼 누르기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌

                    return;
                }

                test_map.put(1, Button1Click);
                test_map.put(2, Button2Click);
                test_map.put(3, Button3Click);
                test_map.put(4, Button4Click);
                test_map.put(5, Button5Click);
                test_map.put(6, Button6Click);
                test_map.put(7, Button7Click);
                test_map.put(8, Button8Click);
                test_map.put(9, Button9Click);


                boolean is_pass = true;

                for(int i=1;i<10;i++){
                    if(test_map.get(i)!=answer_map.get(i)){
                        is_pass=false;
                        break;
                    }
                }

                if(is_pass) {
                    new AlertDialog.Builder(JoinActivity.this)
                            .setTitle("테스트 통과")
                            .setMessage("운전 가능한 상태입니다.")
                            .setNeutralButton("안전운행 하세요", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌
                }else{
                    new AlertDialog.Builder(JoinActivity.this)
                            .setTitle("테스트 실패")
                            .setMessage("음주 하셨나요?")
                            .setNeutralButton("돌아가기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌
                }
            }
        });
    }

    protected String[] GetTestSet() throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.getCaptchatTestSet();
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