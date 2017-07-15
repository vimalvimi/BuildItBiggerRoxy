package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jokeprovider.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnJokeReceivedListener {

    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progressbar);
        Button button = (Button) root.findViewById(R.id.button_joke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });
        return root;
    }

    @Override
    public void onReceived(String joke) {
        mProgressBar.setVisibility(View.GONE);
        startJokeActivity(joke);
    }

    private void startJokeActivity(String j) {
        Intent intent = new Intent(getActivity(), JokeActivity.class);
        intent.putExtra(JokeActivity.INTENT_JOKE, j);
        startActivity(intent);
    }

    public void tellJoke() {
        mProgressBar.setVisibility(View.VISIBLE);
        new EndPointAsync().execute(this);
    }
}
