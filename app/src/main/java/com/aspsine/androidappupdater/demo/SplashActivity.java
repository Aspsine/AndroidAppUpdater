package com.aspsine.androidappupdater.demo;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aspsine.androidappupdater.UpdateReceiver;
import com.aspsine.androidappupdater.UpdateService;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private Button button;

    WeakUpdateReceiver mReceiver = new WeakUpdateReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        button = (Button) findViewById(R.id.btnUpdate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://dl.wandoujia.com/files/jupiter/latest/wandoujia-web_direct_homepage.apk";
                UpdateService.download(SplashActivity.this, url, 3, "v1.0.2");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(UpdateService.ACTION_UPDATE);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    static class WeakUpdateReceiver extends UpdateReceiver {

        public WeakReference<SplashActivity> mReference;

        public WeakUpdateReceiver(SplashActivity splashActivity) {
            this.mReference = new WeakReference<SplashActivity>(splashActivity);
        }

        @Override
        protected void onDownloadStarted() {
            Log.i(TAG, "start");
        }

        @Override
        protected void onDownloadProgressing(int finished, int total, int progress) {
            Log.i(TAG, "progress = "+ progress);
        }

        @Override
        protected void onDownloadCompleted(String apkPath) {
            Log.i(TAG, "complete = "+ apkPath);
        }

        @Override
        protected void onDownloadFailed() {
            Log.i(TAG, "failed");
        }

        @Override
        protected void onDownloadCanceled() {
            Log.i(TAG, "canceled");
        }
    }
}
