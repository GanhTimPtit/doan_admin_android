package com.example.king.managebook.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.king.managebook.R;
import com.example.king.managebook.view.account.login.LoginActivity;

public class SplashAcivity extends AppCompatActivity {

    ProgressBar progressBar;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acivity);
        progressBar = findViewById(R.id.progress);

        new SplashTimer().execute();
    }

    private class SplashTimer extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long millisPerProgress = 2500 / 100;
            int progress = 0;
            try {
                while (progress <= 100) {
                    progress++;
                    publishProgress(progress);
                    Thread.sleep(millisPerProgress);
                }
            } catch (InterruptedException ignored) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // if (!Constants.LOGIN_TRUE.equals(Utils.getSharePreferenceValues(SplashActivity.this, Constants.STATUS_LOGIN))) {
//            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            //   } else {
            startActivity(new Intent(SplashAcivity.this, LoginActivity.class));
            //  }
            finish();
        }
    }

}
