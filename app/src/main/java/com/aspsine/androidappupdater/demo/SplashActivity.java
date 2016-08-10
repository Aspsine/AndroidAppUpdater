package com.aspsine.androidappupdater.demo;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.androidappupdater.UpdateReceiver;
import com.aspsine.androidappupdater.UpdateService;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private TextView tvStatus;
    private TextView tvProgress;
    private ProgressBar progressBar;

    private Button btnUpdate;

    private Button btnCancel;

    WeakUpdateReceiver mReceiver = new WeakUpdateReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://dl.wandoujia.com/files/jupiter/latest/wandoujia-web_direct_homepage.apk";
                UpdateService.download(SplashActivity.this, url, 3, "v1.0.2");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateService.cancelDownload(SplashActivity.this);
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

    private void setDownloadStatus(String status) {
        if (!TextUtils.isEmpty(status)) {
            tvStatus.setText(status);
        } else {
            tvStatus.setText("");
        }
    }

    private void setDownloadProgress(int progress) {
        if (progressBar.getProgress() != progress) {
            tvProgress.setText(progress + "%");
            progressBar.setProgress(progress);
        }
    }

    static class WeakUpdateReceiver extends UpdateReceiver {

        public WeakReference<SplashActivity> mReference;

        public WeakUpdateReceiver(SplashActivity splashActivity) {
            this.mReference = new WeakReference<SplashActivity>(splashActivity);
        }

        @Override
        protected void onDownloadStarted() {
            SplashActivity activity = mReference.get();
            if(activity != null){
                activity.setDownloadStatus("Start");
                activity.setDownloadProgress(0);
            }
        }

        @Override
        protected void onDownloadProgressing(int finished, int total, int progress) {
            SplashActivity activity = mReference.get();
            if(activity != null){
                activity.setDownloadStatus("Downloading");;
                activity.setDownloadProgress(progress);
            }
        }

        @Override
        protected void onDownloadCompleted(String apkPath) {
            SplashActivity activity = mReference.get();
            if(activity != null){
                activity.setDownloadStatus("Complete");;
                activity.setDownloadProgress(100);
            }
        }

        @Override
        protected void onDownloadFailed() {
            SplashActivity activity = mReference.get();
            if(activity != null){
                activity.setDownloadStatus("Failed");;
            }
        }

        @Override
        protected void onDownloadCanceled() {
            SplashActivity activity = mReference.get();
            if(activity != null){
                activity.setDownloadStatus("Canceled");
                activity.setDownloadProgress(0);
            }
        }
    }
}
