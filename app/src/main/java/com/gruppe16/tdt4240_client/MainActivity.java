package com.gruppe16.tdt4240_client;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gruppe16.tdt4240_client.fragments.JoinGameFragment;
import com.gruppe16.tdt4240_client.fragments.MenuFragment;

public class MainActivity extends AppCompatActivity implements JoinGameFragment.JoinGameByPinListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeStartFragment();
    }

    public void initializeStartFragment(){
        MenuFragment fragment = MenuFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onJoinGame(String gamePin) {
        NetworkAbstraction.joinGame(gamePin);
    }
}