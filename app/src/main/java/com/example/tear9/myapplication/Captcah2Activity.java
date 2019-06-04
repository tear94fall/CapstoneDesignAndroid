package com.example.tear9.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Captcah2Activity extends AppCompatActivity {
    ImageView imageview = null;
    private InputMethodManager imm;

    /* 최대 몇회의 시도를 했는지 체크하는 변수 */
    int CountOfTest = 0;
    int MaxCountOfText = 5;

    /* 테스트 시작 버튼을 눌렀는지 체크 하는 변수 */
    boolean test_start_check = false;

    /* 테스트 번호를 저장하는 전역 변수 */
    int captcha_test_number;

    boolean isAlcoholCountUpdate = false;

    // 이미지 번호를 저장 하는 배열
    int[] img = {R.drawable.recaptcha_img1,
            R.drawable.recaptcha_img2,
            R.drawable.recaptcha_img3,
            R.drawable.recaptcha_img4,
            R.drawable.recaptcha_img5,
            R.drawable.recaptcha_img6,
            R.drawable.recaptcha_img7,
            R.drawable.recaptcha_img8,
            R.drawable.recaptcha_img9,
            R.drawable.recaptcha_img10,
            R.drawable.recaptcha_img11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha2);

        setTitle("Captcha2");

        Intent intent = getIntent(); /*데이터 수신*/
        final String user_id = intent.getExtras().getString("user_id"); /*String형*/

        imageview = (ImageView) findViewById(R.id.imageView);
        imageview.setImageResource(R.drawable.warning_img2);

        final EditText user_captcha_answer = (EditText) findViewById(R.id.editText3);

        Button submit_button = findViewById(R.id.button4);
        Button start_button = findViewById(R.id.button5);

        /*
         * 테스트 시작 버튼을 먼저 누름
         * */
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test_start_check = true;   // 테스트 버튼을 눌렀는지 체크 하는 변수를 true로 바꿈

                captcha_test_number = make_random_number(); // 캡차 테스트 숫자를 초기화함
                imageview.setImageResource(img[captcha_test_number]); // 이미지를 변경함
            }
        });


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(user_captcha_answer.getWindowToken(), 0);

                // 테스트 시작 버튼을 누르지 않았을 경우
                if (!test_start_check) {
                    Toast.makeText(getApplicationContext(), "테스트 시작버튼을 먼저 눌러주세요.", Toast.LENGTH_LONG).show();
                } else {
                    // EditText에 입력된 값을 가져온뒤 서버로 보냄

                    String temp_captch_answer = user_captcha_answer.getText().toString();

                    // 입력된 값이 없을떄
                    if (temp_captch_answer.length() == 0) {
                        Toast.makeText(getApplicationContext(), "값을 입력해주세요.", Toast.LENGTH_LONG).show();
                    } else {
                        // 값이 입력되었을 경우
                        // 캡챠 테스트 넘버는 0~11까지 생성함
                        // 디비에 저장되어있는 숫자가 1부터 시작하므로 1더해서 보냄
                        String temp_captcha_test_number = Integer.toString(captcha_test_number + 1);
                        String result[] = new String[1];
                        try {
                            result = getCaptcah2TestSet(temp_captcha_test_number, temp_captch_answer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        /* 캡챠 정답이거나 아닐 경우 처리 할 로직 */
                        if ("true".equals(result[0])) {
                            Toast.makeText(getApplicationContext(), "테스트에 통과 하셨습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            if (MaxCountOfText > CountOfTest) {
                                Toast.makeText(getApplicationContext(), "다시 시도해주세요. " + Integer.toString(MaxCountOfText - CountOfTest) + " 회의 기회가 남았습니다.", Toast.LENGTH_LONG).show();
                                CountOfTest++;
                                return;
                            } else {
                                Toast.makeText(getApplicationContext(), "최대 시도 횟수를 초과 하셨습니다.", Toast.LENGTH_LONG).show();
                                if(!isAlcoholCountUpdate){
                                    isAlcoholCountUpdate=true;
                                    /* 알코올 운전 카운트 추가*/
                                    try {
                                        UpdateAlcholCount(user_id);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                return;
                            }
                            // 넘어서면 종료 시킴
                        }
                        Handler timer = new Handler(); //Handler 생성

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), TestPassActivity.class);
                                intent.putExtra("user_id", user_id); /*송신*/
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                }
            }
        });
    }

    // 입력된 캡챠의 테스트 번호와 입력된 캡챠의 값을 서버로 전송함
    protected String[] getCaptcah2TestSet(final String captcha_test_number, final String captcha_test_answer) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.getCaptchatTestSet2(captcha_test_number, captcha_test_answer);
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

    protected void UpdateAlcholCount(final String _id) throws IOException, InterruptedException {
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.UpdateAlcoholCount(_id);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        sleep(1000);
    }

    // 숫자를 랜덤으로 생성하는 함수
    static int make_random_number() {
        int number;
        number = (int) (Math.random() * 11);
        return number;
    }
}