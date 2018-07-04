package com.rarora.barcodewifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class FinalActivity extends AppCompatActivity {
    Intent intent;
    TextView textView;
    String code;
    Button button;
    String[] stArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        intent = getIntent();
        textView = (TextView) findViewById(R.id.textView);
        code = (String) intent.getStringExtra("barcode");
        button = (Button) findViewById(R.id.button2);

        StringTokenizer st = new StringTokenizer(code, ":;");
        stArr = new String[st.countTokens()];
        if (stArr.length == 4) {
            while (st.hasMoreElements()) {
                for (int i = 0; i < 4; i++) {
                    stArr[i] = (String) st.nextElement();
                }
            }
            String textText = stArr[0] + ": " + stArr[1] + "\n" + stArr[2] + ": " + stArr[3];
            String buttonText = "  " + "Connect to '" + stArr[1] + "'  ";
            textView.setText(textText);
            button.setText(buttonText);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "Connecting to '" + stArr[1] + "'", Toast.LENGTH_LONG).show();

                }
            });
        } else {
            String buttonText = "  " + "Restart" + "  ";
            textView.setText(code);
            button.setText(buttonText);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent process = new Intent(FinalActivity.this, MainActivity.class);
                    FinalActivity.this.startActivity(process);
                }
            });
        }
    }
}
