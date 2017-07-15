package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;

import com.example.batman.myapplication.backend.myApi.MyApi;
import com.example.batman.myapplication.backend.myApi.model.MyBean;
import com.example.jokeprovider.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


public class EndPointAsync extends AsyncTask<OnJokeReceivedListener, Void, String> {
    private static MyApi mJokeApi = null;
    private String mResult;
    private OnJokeReceivedListener listener;

    private static final String TAG = "EndPointAsync";

    @Override
    protected String doInBackground(OnJokeReceivedListener... params) {
        if (mJokeApi == null) {
            String root_url_api = "http://10.0.2.2:8080/_ah/api/";
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(root_url_api);
            mJokeApi = builder.build();
        }

        listener = params[0];

        try {
            return mJokeApi.sayJoke(new MyBean()).execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onReceived(s);
    }
}
