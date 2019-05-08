package com.example.tear9.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tear9.myapplication.MsgPacker.Client;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class RegisterActivity extends AppCompatActivity {
    String ID_checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button Register_request_button = findViewById(R.id.Register_Request);
        Button ID_check = findViewById(R.id.ID_Check);
        Button register_Cancel = findViewById(R.id.register_Cancel);


        // 아이디 중복 검사를 위한 변수
        ID_checker = "none";

        final TextView input_id = (TextView) findViewById(R.id.UserIDInput);
        final TextView input_pw = (TextView) findViewById(R.id.UserPWInput);
        final TextView input_pw_check = (TextView) findViewById(R.id.userPWCheckInput);
        final TextView input_name = (TextView) findViewById(R.id.UserNameInput);
        final TextView input_tel = (TextView) findViewById(R.id.userTelInput);


        register_Cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /* 회원가입 버튼이 눌렸을 경우에는 무조건 회원 가입 페이지로 화면 전환 */
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // 아이디 중복 검사를 위한 버튼 클릭 이벤트
        ID_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 중복 아이디가 존재 하지 않을 경우 ID_checker 변수를 True로 만듦
                // 아이디 중복검사를 위한 로직 추가
                TextView input_id = (TextView) findViewById(R.id.UserIDInput);
                String id_str = input_id.getText().toString();

                String result[] = new String[1];

                try {
                    result = loginIdCheck(id_str);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 중복 아이디가 존재 하지 않을때
                if (!("true".equals(result[0]))) {
                    ID_checker = "success";

                    // 중복 아이디가 없으면 그대로 로직 수행
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("사용할수 있는 아이디 입니다.")
                            .setMessage("계속 회원가입을 진행 해주세요")
                            .setNeutralButton("계속 진행하기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌
                } else {
                    // 중복 아이디 존재
                    ID_checker = "exist";
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

        //회원 가입 버튼 클릭 이벤트
        Register_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView input_id = (TextView) findViewById(R.id.UserIDInput);
                TextView input_pw = (TextView) findViewById(R.id.UserPWInput);
                TextView input_pw_check = (TextView) findViewById(R.id.userPWCheckInput);
                TextView input_name = (TextView) findViewById(R.id.UserNameInput);
                TextView input_tel = (TextView) findViewById(R.id.userTelInput);

                String id = input_id.getText().toString();
                String pw = input_pw.getText().toString();
                String pw_check = input_pw_check.getText().toString();
                String name = input_name.getText().toString();
                String tel = input_tel.getText().toString();

                /*
                회원 가입이 안되야 하는 경우

                1. 중복 아이디 존재 ( 아이디 중복을 체크하는 버튼 추가할 것)
                2. 아이디 빈값
                3. 비밀번호 빈값
                 */

                if("success".equals(ID_checker)){
                    //회원 가입 진행 하는데 모든 경우의 수를 다 통과해야 회원가입 진행

                    // 비밀 번호가 다를 경우 팝업창을 띄움
                    if(!pw.equals(pw_check)){
                        if (pw.trim().length() == 0 || pw_check.trim().length()==0) {
                            // 패스워드가 빈값을 경우
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("비밀번호를 입력해주세요")
                                    .setMessage("올바른 비밀번호를 입력해주세요")
                                    .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dlg, int sumthin) {
                                        }
                                    }).show(); // 팝업창 보여줌
                        }else {
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("비밀번호가 일치하지 않습니다.")
                                    .setMessage("비밀번호를 일치시켜 주세요")
                                    .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dlg, int sumthin) {
                                        }
                                    }).show(); // 팝업창 보여줌
                        }
                    }else if (id.trim().length() == 0) {
                        // 아이디가 빈값일 경우
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("아이디를 입력해주세요")
                                .setMessage("올바른 아이디를 입력해주세요")
                                .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    }
                                }).show(); // 팝업창 보여줌
                    } else if (name.trim().length() == 0){
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("이름을 입력해주세요")
                                .setMessage("올바른 이름을 입력해주세요")
                                .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    }
                                }).show(); // 팝업창 보여줌
                    }else if (tel.trim().length() == 0){
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("전화번호를 입력해주세요")
                                .setMessage("올바른 전화번호를 입력해주세요")
                                .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    }
                                }).show(); // 팝업창 보여줌
                    }else{
                        // 회원 가입 진행함
                        String result[] = new String[1];
                        try{
                            // 회원가입 처리 시작
                            result = CreateAccount(id, pw, name, tel);

                            // 회원가입 결과 처리 시작
                            if ("true".equals(result[0])) {
                                // 회원가입에 성공했을 경우 로그인 화면으로 이동함
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // 회원가입에 실패 했을경우
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setTitle("회원 가입 실패.")
                                        .setMessage("다시 한번 진행해 주세요")
                                        .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dlg, int sumthin) {
                                            }
                                        }).show(); // 팝업창 보여줌
                            }
                        } catch (Exception e){
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("회원 가입 실패.")
                                    .setMessage("다시 한번 진행해 주세요")
                                    .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dlg, int sumthin) {
                                        }
                                    }).show(); // 팝업창 보여줌
                        }
                    }
                }else {
                    // 중복 아이디 존재
                    if("exist".equals(ID_checker)) {
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("이미 등록된 아이디 입니다.")
                                .setMessage("새로운 아이디를 입력해주세요")
                                .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    }
                                }).show(); // 팝업창 보여줌
                    }else if ("none".equals(ID_checker)){
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("존재하는 아이디 인지 확인해주세요.")
                                .setMessage("아이디 중복검사를 해주세요")
                                .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    }
                                }).show(); // 팝업창 보여줌
                    }
                }
            }
        });
    }

    // 서버에 입력된 아이디가 존재하는지 존재하지 않는지 보낸뒤 값을 받아옴
    protected String[] loginIdCheck(final String _id) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.IdCheck(_id);
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

    protected String[] CreateAccount(final String _id, final String _passwd, final String _name, final String _tel) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.CreateAccount(_id, _passwd, _name, _tel);
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
