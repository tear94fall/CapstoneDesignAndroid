package com.example.tear9.myapplication;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    /* 중복 클릭 방지를 위한 변수 */
    private static final long MIN_CLICK_INTERVAL = 1000;
    private long mLastClickTime;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 상단 타이틀 변경
        setTitle("로그인");
        backPressCloseHandler = new BackPressCloseHandler(this);

        final Button button = findViewById(R.id.button);
        final Button reg_button = findViewById(R.id.Register);
        final Button find_user_info_button = findViewById(R.id.find_user_info_button);

        final int[] login_cnt = {0};

        /* 네트워크가 연결 되어있는지 확인 함*/
        NetworkInfo mNetworkState = getNetworkInfo();
        String alertMessage = null;

        if (mNetworkState != null && mNetworkState.isConnected()) {
            if (mNetworkState.getType() == ConnectivityManager.TYPE_WIFI) {
                alertMessage = "WiFi 네트워크 연결됨";
            } else if (mNetworkState.getType() == ConnectivityManager.TYPE_MOBILE) {
                alertMessage = "모바일(LTE/5G) 네트워크에 연결됨";
            }


            /* 서버와의 연결을 테스트 함 */
            String temp_str = "test";
            String result[] = new String[0];
            result = null;
            try {
                result = CheckEchoRequest(temp_str);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (result[0] == null) {
                Toast.makeText(getApplicationContext(), "서비스 점검중 입니다.", Toast.LENGTH_LONG).show();
                button.setEnabled(false);
                reg_button.setEnabled(false);

                TextView id = (TextView) findViewById(R.id.id_input_edit_text);
                TextView pw = (TextView) findViewById(R.id.pw_input_edit_text);
                id.setInputType(InputType.TYPE_NULL);
                pw.setInputType(InputType.TYPE_NULL);

                Handler timer = new Handler(); //Handler 생성

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.finish();
                    }
                }, 2000);

            } else {
                Toast.makeText(getApplicationContext(), alertMessage, Toast.LENGTH_LONG).show();
            }
        } else {
            alertMessage = "네트워크가 연결되지 않았습니다.";
            Toast.makeText(getApplicationContext(), alertMessage, Toast.LENGTH_LONG).show();
            Handler timer = new Handler(); //Handler 생성

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.this.finish();
                }
            }, 2000);
        }

        // 로그인 버튼 눌렀을 경우
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;

                // 중복 클릭인 경우
                if (elapsedTime <= MIN_CLICK_INTERVAL) {
                    return;
                }

                TextView id = (TextView) findViewById(R.id.id_input_edit_text);
                TextView pw = (TextView) findViewById(R.id.pw_input_edit_text);

                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pw.getWindowToken(), 0);

                String id_str = id.getText().toString();
                String pw_str = pw.getText().toString();

                TextView textView1 = (TextView) findViewById(R.id.textView1);

                if (id_str.replace(" ", "").equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pw_str.replace(" ", "").equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }

                // 로그인 성공 여부 확인 하는 함수
                try {
                    String result[] = logInCheck(id_str, pw_str);
                    boolean checker = false;

                    if ("true".equals(result[0])) {
                        checker = true;
                    }

                    if (checker) {
                        /* 로그인 성공한 다음 */
                        /* 테스트 시작 페이지로 이동함*/
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.putExtra("user_id", id_str); /*송신*/
                        startActivity(intent);
                        finish();
                    } else {
                        /* 로그인 실패시 현재 페이지 에서 팝업창 띄울것 */
                        /* 일정 횟수 이상 넘어갈 경우에는 블로킹 되도록 처리 함*/
                        login_cnt[0] += 1;

                        if (login_cnt[0] == 5) {
                            button.setEnabled(false);
                        } else if(login_cnt[0]>5){
                            Toast.makeText(getApplicationContext(), "회원 정보 찾기를 눌러 주세요.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패. 아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 회원가입 버튼이 눌렸을 경우
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 회원가입 버튼이 눌렸을 경우에는 무조건 회원 가입 페이지로 화면 전환 */
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // 네트워크 상태를 체크하는 메소드
    private NetworkInfo getNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    // 서버에게 보내는 로그인 요청 메소드
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

        sleep(1000);
        return result;
    }

    // 서버에게 에코 요청을 보냄
    // 서버가 켜져있는지 확인 하는 메소드
    protected String[] CheckEchoRequest(final String echo_test_value) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.EchoRequest(echo_test_value);
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
