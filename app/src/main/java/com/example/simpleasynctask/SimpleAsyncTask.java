package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Integer, String> {
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask(TextView tv, ProgressBar pb) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(pb);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Random r = new Random();

        int n = r.nextInt(11);

        int s = n * 200;

        mProgressBar.get().setMax(s);
        mProgressBar.get().setProgress(0,true);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();

        int n = r.nextInt(11);

        int s = n * 200;
            try {
                for (int i = 1; i<= n; i++) {
                    Thread.sleep(s);
                    publishProgress(i * 200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Awake at last after sleeping for " + s + " milliseconds!";
    }


    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mTextView.get().setText("Sleep for "+ values[0] +" milliseconds");
        mProgressBar.get().incrementProgressBy(200);
    }
}
