package com.rarora.barcodewifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.rarora.barcodereader.BarcodeReader;

import java.util.List;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        View view = (View) findViewById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {
        // single barcode scanned
        String code = barcode.rawValue;
        Intent intent = new Intent(ScanActivity.this, FinalActivity.class);
        intent.putExtra("numCodes", "single");
        intent.putExtra("barcode", code);
        ScanActivity.this.startActivity(intent);
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {
        Toast.makeText(getBaseContext(), "Please scan one code at a time", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        // barcode scanned from bitmap image
    }

    @Override
    public void onScanError(String s) {
        // scan error
    }

    @Override
    public void onCameraPermissionDenied() {
        // camera permission denied
    }
}
