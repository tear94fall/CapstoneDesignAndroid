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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView id = (TextView)findViewById(R.id.editText2);
                TextView pw = (TextView)findViewById(R.id.editText);

                final String id_str = id.getText().toString();
                final String pw_str = pw.getText().toString();

                TextView textView1 = (TextView) findViewById(R.id.textView4);
                if(id_str != "") {
                    textView1.setText(id_str);
                }
                /*
                Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                startActivity(intent);
                finish();
                */
            }
        });
    }

    public void onButtonClicked(View v){
        Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
        startActivity(intent);
        finish();
    }

    public void onButton1Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
        startActivity(intent);
        finish();
    }
}
