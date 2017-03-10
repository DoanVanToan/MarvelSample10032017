package com.framgia.toandoan.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.framgia.toandoan.R;
import com.framgia.toandoan.apiservice.APIUtils;
import com.framgia.toandoan.apiservice.MarvelService;
import com.framgia.toandoan.apiservice.ServiceGenerator;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
    View.OnClickListener {
    private TextView mTextResult;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_start).setOnClickListener(this);
        mTextResult = (TextView) findViewById(R.id.text_result);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_start) {
            getCharacterByApi();
        }
    }

    private void initDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);
        mDialog.setMessage("Loading...");
    }

    private void showDialog() {
        if (mDialog == null) initDialog();
        if (!mDialog.isShowing()) mDialog.show();
    }

    private void dissmissDialog() {
        if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
    }

    private void getCharacterByApi() {
        showDialog();
        long timeStamp = System.currentTimeMillis();
        ServiceGenerator
            .createService(MarvelService.class)
            .getCharacters(timeStamp, APIUtils.API_KEY, APIUtils.getKey(timeStamp))
            .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        mTextResult.setText("!response isSuccessful");
                        return;
                    }
                    try {
                        mTextResult.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        mTextResult.setText("IOException: " + e.toString());
                    }
                    dissmissDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mTextResult.setText("onFailure");
                    dissmissDialog();
                }
            });
    }
}
