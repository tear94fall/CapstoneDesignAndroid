package com.example.tear9.myapplication;

import android.app.Activity;
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

public class ModifyActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Button modify_button = findViewById(R.id.modify_button);
        Button cancel_button = findViewById(R.id.cancel_button);

        final TextView user_id_editText = (TextView) findViewById(R.id.UserId_editText);
        TextView user_pw_editText = (TextView) findViewById(R.id.UserPasswd_editText);
        TextView user_name_editText = (TextView) findViewById(R.id.UserName_editText);
        TextView user_tel_editText = (TextView) findViewById(R.id.UserTel_editText);

        /* 로그인된 사용자의 정보를 가져옴 */
        Intent intent = getIntent(); /*데이터 수신*/
        final String user_id = intent.getExtras().getString("user_id"); /*String형*/
        String[] userInfo = new String[1];

        String user_id_str = null;
        String user_passwd_str = null;
        String user_name_str = null;
        String user_tel_str = null;

        try {
            userInfo = getAllUserInfo(user_id);
            String tempUserInfo = userInfo[0];

            String[] userInfoArray = tempUserInfo.split("-");

            user_id_str = userInfoArray[0];
            user_passwd_str = userInfoArray[1];
            user_name_str = userInfoArray[2];
            user_tel_str = userInfoArray[3];

            user_id_editText.setText(user_id_str);
            user_pw_editText.setText(user_passwd_str);
            user_name_editText.setText(user_name_str);
            user_tel_editText.setText(user_tel_str);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String finalUser_id_str = user_id_str;
        final String finalUser_passwd_str = user_passwd_str;
        final String finalUser_name_str = user_name_str;
        final String finalUser_tel_str = user_tel_str;

        modify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView user_id_modify = (TextView) findViewById(R.id.UserId_editText);
                TextView user_pw_modify = (TextView) findViewById(R.id.UserPasswd_editText);
                TextView user_name_modify = (TextView) findViewById(R.id.UserName_editText);
                TextView user_tel_modify = (TextView) findViewById(R.id.UserTel_editText);

                String id_str = user_id_modify.getText().toString();
                String pw_str = user_pw_modify.getText().toString();
                String name_str = user_name_modify.getText().toString();
                String tel_str = user_tel_modify.getText().toString();

                if(!id_str.equals(finalUser_id_str)){
                    new AlertDialog.Builder(ModifyActivity.this)
                            .setTitle("아이디 변경불가")
                            .setMessage("다른 항목을 수정해주세요.")
                            .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌
                    user_id_editText.setText(finalUser_id_str);
                    return;
                }
                if(pw_str.equals(finalUser_passwd_str)&&name_str.equals(finalUser_name_str)&&tel_str.equals(finalUser_tel_str)){
                    new AlertDialog.Builder(ModifyActivity.this)
                            .setTitle("업데이트 실패")
                            .setMessage("변경할 내용이 없습니다")
                            .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }
                            }).show(); // 팝업창 보여줌
                }else{
                    String result[] = new String[0];
                    try {
                        result = ModifyUserInfo(id_str, pw_str, name_str, tel_str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if ("true".equals(result[0])) {
                        try {
                            String[] test = new String[1];
                            test = getAllUserInfo(finalUser_id_str);
                            String temp_result = test[0];
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        new AlertDialog.Builder(ModifyActivity.this)
                                .setTitle("업데이트 성공")
                                .setMessage("변경이 완료 되었습니다.")
                                .setNeutralButton("돌아가기", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    }
                                }).show(); // 팝업창 보여줌
                        // 완료후 이전 액티비티로 이동
                        onBackPressed();
                    }else{
                        new AlertDialog.Builder(ModifyActivity.this)
                                .setTitle("업데이트 실패")
                                .setMessage("변경된 내용이 없습니다")
                                .setNeutralButton("다시 시도", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                    }
                                }).show(); // 팝업창 보여줌
                    }
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("뒤로 가기");
                onBackPressed();
            }
        });
    }


    // 사용자의 모든 정보를 가져오는 클래스
    protected String[] getAllUserInfo(final String _id) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.getAllUserInfoClass(_id);
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

    // 사용자의 정보를 변경하는 메소드
    protected String[] ModifyUserInfo(final String _id, final String _pw, final String _name, final String _tel) throws IOException, InterruptedException {
        final String[] result = new String[1];
        new Thread() {
            public void run() {
                try {
                    Client client = new Client();
                    client.UpdateUserInfo(_id, _pw, _name, _tel);
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