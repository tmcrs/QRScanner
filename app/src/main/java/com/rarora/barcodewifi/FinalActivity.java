package com.rarora.barcodewifi;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;

public class FinalActivity extends AppCompatActivity {
    Intent intent;
    TextView textView;
    String code, wifiSSID, wifiPassword;
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
        if (stArr.length == 7) {
            while (st.hasMoreElements()) {
                for (int i = 0; i < 7; i++) {
                    stArr[i] = (String) st.nextElement();
                }
            }
            wifiSSID = stArr[2];
            wifiPassword = stArr[6];

            String textText = "SSID: " + wifiSSID + "\n" + "Password: " + wifiPassword;
            String buttonText = "  " + "Connect to " + wifiSSID + "  ";
            textView.setText(textText);
            button.setText(buttonText);
            button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wifiConnect();
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

    public void wifiConnect(){
        Toast.makeText(getBaseContext(), "Connecting to '" + wifiSSID + "'", Toast.LENGTH_LONG).show();

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = wifiSSID;
        conf.preSharedKey = wifiPassword;
        conf.status = WifiConfiguration.Status.ENABLED;
        conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        int code = wifiManager.addNetwork(conf);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        wifiManager.disconnect();
        wifiManager.enableNetwork(code, true);
        wifiManager.reconnect();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + wifiSSID + "\"")) {
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
    }
}
