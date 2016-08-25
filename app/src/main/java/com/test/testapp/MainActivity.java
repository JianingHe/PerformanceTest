package com.test.testapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mInputText = null;
    private Button mStartBtn = null;
    private TextView mConsumeText = null;
    private TextView mResultText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputText = (EditText) findViewById(R.id.input);
        mStartBtn = (Button) findViewById(R.id.start_btn);
        mResultText = (TextView) findViewById(R.id.result_txt);
        mConsumeText = (TextView) findViewById(R.id.consume_txt);

        if (mStartBtn != null && mInputText != null && mConsumeText != null) {
            mStartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String numString = mInputText.getText().toString();
                    int num = 0;
                    try {
                        num = Integer.valueOf(numString);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }

                    if (num > 0) {
                        fibonacciAsync(num);
                    }
                }
            });
        }
    }

    void fibonacciAsync(int num) {
        new AsyncTask<Integer, Long, Long>() {

            @Override
            protected Long doInBackground(Integer... integers) {

                long start = System.currentTimeMillis();
                long res = computeRecursively(integers[0]);
                long end = System.currentTimeMillis();
                long consume = end - start;

                publishProgress(consume);

                return res;
            }

            @Override
            protected void onProgressUpdate(Long... values) {
                mConsumeText.setText(String.valueOf(values[0]));
            }

            @Override
            protected void onPostExecute(Long aLong) {
                mResultText.setText(String.valueOf(aLong));
            }

        }.execute(num);
    }


    private static long computeRecursively(int n) {
        if (n > 1)
            return computeRecursively(n-2) + computeRecursively(n-1);
        return n;
    }

}
