package com.gruppe16.tdt4240_client.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.gruppe16.tdt4240_client.DrawingView;
import com.gruppe16.tdt4240_client.NetworkAbstraction;
import com.gruppe16.tdt4240_client.R;

import org.json.JSONObject;

public class GuessFragment extends Fragment {

    private TextView guess;
    private TextView timeLeftTextView;
    private Button submitButton;
    private GuessFragment.OnSubmitGuessListener mListener;
    //private Canvas canvas;
    //private DrawingView drawingView;
    private ImageView imageView;

    public GuessFragment() {
        // Required empty public constructor
    }

    public static GuessFragment newInstance() {
        GuessFragment fragment = new GuessFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_draw, container, false);
        timeLeftTextView = (TextView) rootView.findViewById(R.id.timeLeftTextView);
        guess = (TextView) rootView.findViewById(R.id.guessWord);
        imageView = (ImageView) rootView.findViewById(R.id.waiting);
        //drawingView = (DrawingView) rootView.findViewById(R.id.drawingView);
        //canvas = (Canvas) drawingView.getCanvas();
        submitButton = (Button) rootView.findViewById(R.id.submitButton);

        imageView.setVisibility(View.VISIBLE);
        guess.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.VISIBLE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guessedWord = guess.getText().toString();
                String gamepin = "2"; //TODO get real gamepin
                NetworkAbstraction.getInstance(getContext()).submitGuess(getContext(), gamepin, guessedWord,new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("SvarGuess:"+response);
                    }
                });
            }
        });

        //Drawable d = ContextCompat.getDrawable(getActivity(), R.drawable.waiting);
        //d.setBounds(0, 0, 0, 0);
        //d.draw(canvas);

        //The countdown timer
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftTextView.setText("Seconds left: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                timeLeftTextView.setText("Seconds left: 0");
                //drawingView.stopDraw();
                //finishedDrawing = drawingView.getFinishedDrawing();
                //someImageView.setImageBitmap(finishedDrawing);
                String gamepin = "3"; //TODO: Get gameping from real location.

                /*
                NetworkAbstraction.getInstance(getContext()).submitDrawing(getContext(), gamepin, finishedDrawing,new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Svar:"+response);
                    }
                });*/
            }
        }.start();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSubmitGuessListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
