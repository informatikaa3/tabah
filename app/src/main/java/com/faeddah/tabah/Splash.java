package com.faeddah.tabah;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Splash extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(Splash.this, Home.class));
                finish();

            }
        }, 1234);
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        //Checking the request code of our request
//        if (requestCode == 23) {
//
//            //If permission is granted
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Akses di Setujui", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, "Aplikasi ini memerlukan beberapa askes", Toast.LENGTH_LONG).show();
//            }
//        }
//        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
//            Map<String, Integer> perms = new HashMap<String, Integer>();
//
//            perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
//            perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
//            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
//            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
//            perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
//            for (int i = 0; i < permissions.length; i++)
//                perms.put(permissions[i], grantResults[i]);
//            if ( perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
//                    perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
//                    perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
//                    perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
//                    perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(Splash.this, Home.class);
//                        startActivity(i);
//                        finish();
//
//                    }
//
//                }, 1234);
//
//            } else {
//                // Permission Denied
//                Toast.makeText(Splash.this, "Aplikasi ini memerlukan beberapa askes", Toast.LENGTH_SHORT)
//                        .show();
//                finish();
//            }
//
//        }
//    }
}
