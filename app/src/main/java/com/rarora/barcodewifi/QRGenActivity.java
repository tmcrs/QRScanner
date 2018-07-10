package com.rarora.barcodewifi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.rarora.barcodereader.BarcodeEncoder;

public class QRGenActivity extends AppCompatActivity {
    EditText SSIDInput, PasswordInput;
    Button generateBtn, restartBtn, saveQRBtn;
    MultiFormatWriter writer;
    ImageView imageView;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgen);
        SSIDInput = (EditText) findViewById(R.id.editText);
        PasswordInput = (EditText) findViewById(R.id.editText2);
        generateBtn = (Button) findViewById(R.id.button3);
        restartBtn = (Button) findViewById(R.id.button6);
        saveQRBtn = (Button) findViewById(R.id.button7);
        writer = new MultiFormatWriter();
        imageView = (ImageView) findViewById(R.id.imageView);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(PasswordInput.getWindowToken(), 0);

                String SSID = SSIDInput.getText().toString();
                String password = PasswordInput.getText().toString();
                //WIFI:S:"mySSID";T:"Type_Str";P:”password"
                String converted = "WIFI:S:\"" + SSID + "\";T:\"Type_STR\";P:\"" + password + "\"";
                try {
                    BitMatrix bitMatrix = writer.encode(converted, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRGenActivity.this, MainActivity.class);
                QRGenActivity.this.startActivity(intent);
            }
        });

        saveQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "QR Code" , "");
                Toast.makeText(getBaseContext(), "Saved QR code to gallery", Toast.LENGTH_LONG).show();
            }
        });
    }
}
