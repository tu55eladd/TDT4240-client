package com.gruppe16.tdt4240_client.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.gruppe16.tdt4240_client.FragmentChanger;
import com.gruppe16.tdt4240_client.NetworkAbstraction;
import com.gruppe16.tdt4240_client.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class CreateGameFragment extends Fragment implements Response.Listener<JSONObject> {

    private TextView playersCountTextView;
    private TextView gamePinTextView;
    private Button startGameButton;
    private Timer playerPollTimer;
    private int playersCount;
    private String gamePin;

    public CreateGameFragment() {
        // Required empty public constructor
    }

    public static CreateGameFragment newInstance() {
        CreateGameFragment fragment = new CreateGameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setPin(String gamePin){
        this.gamePin = gamePin;
        setPollingForGame();
        gamePinTextView.setText("PIN: " + gamePin);
    }

    public void setPlayers(int playerCount){
        playersCountTextView.setText(playerCount + "");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_game, container, false);
        gamePinTextView = (TextView) rootView.findViewById(R.id.gamePinTextView);
        playersCountTextView = (TextView) rootView.findViewById(R.id.playersCountTextView);
        startGameButton = (Button) rootView.findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPollTimer.cancel();
                FragmentChanger.goToDrawView(getActivity());
            }
        });

        /* Request new gamepin from server */
        NetworkAbstraction.getInstance(getContext()).createGame(this);

        return rootView;
    }

    private void setPollingForGame(){
        playerPollTimer = new Timer();
        final Response.Listener listener = this;
        playerPollTimer.scheduleAtFixedRate( new TimerTask() {
            @Override
            public void run() {
                NetworkAbstraction.getInstance(getContext()).pollForGame(gamePin, listener);
            }
        }, 0, 1000);
    }

    @Override
    public void onResponse(JSONObject response) {
        try{
            if(gamePin == null){
                System.out.println(response);
                gamePin = response.getString("gamePin");
                this.setPin(gamePin);
            }
            else{
                getPlayers(response);
            }
        }
        catch (JSONException e){
            System.out.println(e);
        }
    }

    private void getPlayers(JSONObject response) throws JSONException{
        System.out.println(response);
        //JSONArray players = response.getJSONArray("players");
        //this.setPlayers(players.length());
    }



}
