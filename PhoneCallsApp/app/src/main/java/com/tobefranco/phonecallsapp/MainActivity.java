package com.tobefranco.phonecallsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CALL_PHONE = 1;
    private TelephonyManager mTelephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(isTelephonyEnabled()){
            checkForPhonePermission();

        }else{
            Toast.makeText(this,
                    R.string.telephony_not_enabled,
                    Toast.LENGTH_LONG).show();
            disableCallButton();
        }
    }

    private void disableCallButton() {
        Button btnCall = (Button) findViewById(R.id.btn_direct_call);
        btnCall.setEnabled(false);
    }

    private boolean isTelephonyEnabled(){
        if(mTelephonyManager != null){
            if(mTelephonyManager.getSimState() ==
                    TelephonyManager.SIM_STATE_READY){
                return true;
            }
        }
        return false;
    }

    public void dialHotline(View view){
        String phone =  "tel: +524451250113";
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse(phone));
        if(dialIntent.resolveActivity(getPackageManager()) != null){
            startActivity(dialIntent);
        }else{
            Log.e("Error", "No app for making calls");
        }
    }

    private void checkForPhonePermission(){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED){
            // Permission not granted.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_REQUEST_CALL_PHONE);
        }else{
            // permission already granted
        }
    }

    @Override // Check permission result.
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CALL_PHONE:{
                if(permissions[0].equalsIgnoreCase(Manifest.permission.CALL_PHONE)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission Granted
                }else {
                    // permission denied
                    Toast.makeText(this,
                            getString(R.string.failure_permission),
                            Toast.LENGTH_LONG).show();
                    disableCallButton();
                }
            }
        }
    }

    public void callNumber(View view){ // Call directly from app.
        String number = "tel: +524451250113";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(number));
        if(callIntent.resolveActivity(getPackageManager()) != null){
            checkForPhonePermission();
            startActivity(callIntent);
        } else {
            Log.e("DaTag", "Can't resolve app");
        }
    }
}
