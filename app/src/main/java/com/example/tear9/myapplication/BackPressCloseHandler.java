package com.example.tear9.myapplication;

import android.app.Activity;
import android.widget.Toast;

class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;
    BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        } if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish(); toast.cancel();
        }
    }

    private void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
