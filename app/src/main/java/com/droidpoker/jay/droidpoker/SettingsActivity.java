package com.droidpoker.jay.droidpoker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.lang.reflect.Array;

import poker.TexasHoldem;


public class SettingsActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    protected Spinner numPlayers_spinner;
    protected TexasHoldem game;
    protected int desiredNumPlayers;
    protected boolean numPlayersChanged;
    protected boolean entryCashChanged;
    protected int startingCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bundle bundle = getIntent().getExtras();
        game = (TexasHoldem)bundle.getSerializable("game");
        Log.d( "JDEBUG", "player 1 name --> " + game.getPlayer(0).getName() );
        Log.d( "JDEBUG", "player 2 name --> " + game.getPlayer(1).getName() );
        numPlayers_spinner = (Spinner) findViewById(R.id.num_players_spinner);
        ArrayAdapter<CharSequence> charAdapter =
                ArrayAdapter.createFromResource(this, R.array.num_players_array,
                        android.R.layout.simple_spinner_item);
        charAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numPlayers_spinner.setAdapter(charAdapter);
        numPlayers_spinner.setOnItemSelectedListener(this);
        // intialize game parametes
        desiredNumPlayers = 2;
        numPlayersChanged = false;
        entryCashChanged = false;
        startingCash = 1500;
    }

//    @Override
//    public void onStop(){
//        // this will update the texasHoldem object when the options menu is backed out of..
//        // called when back button is pressed
//        if(numPlayersChanged)
//            setNumPlayers(desiredNumPlayers, game);
//
//        // send class back to EnterAppActivity
//        Intent intent = new Intent(SettingsActivity.this, EnterAppActivity.class);
//        intent.putExtra("gameFromSettings", game);
//        setResult(Activity.RESULT_OK, intent);
//        super.onStop();
//        finish();
//    }

    @Override
    public void onBackPressed(){
        if(numPlayersChanged)
            setNumPlayers(desiredNumPlayers, game);

        // send class back to EnterAppActivity
        Intent intent = new Intent(SettingsActivity.this, EnterAppActivity.class);
        intent.putExtra("gameFromSettings", game);
        setResult(Activity.RESULT_OK, intent);
        //finish();
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // number of players selected bu user
        Log.d("JDEBUG: ", "ITEM SELECTED at position: " + position);
        SpinnerAdapter sa = numPlayers_spinner.getAdapter();
        desiredNumPlayers = Integer.parseInt( (String)sa.getItem(position) );
        numPlayersChanged = true;
        System.out.println("JDEBUG::dataPt: " + desiredNumPlayers);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private static void setNumPlayers(int numPlayers, TexasHoldem game){
        // sets number of players in game obj
        // set number of players
        System.out.println("SetNumPlayers called...");
        if(game.getNumPlayers() < numPlayers){
            int num_additional_players = numPlayers - game.getNumPlayers();
            Integer n = game.getNumPlayers() + 1;
            System.out.println("JDEBUG:: adding players now....");
            System.out.println("JDEBUG:: number of additional players = " + num_additional_players);

            for(int i=0; i<num_additional_players; i++) {
                System.out.println("JDEBUG::" + num_additional_players + "Players entering the game!!!!");
                game.enterGame("Player " + n.toString());
                n++;
            }
        }
        else if(game.getNumPlayers() > numPlayers){
            // need to remove extra players to the desired amount of players
            System.out.println("JDEBUG:: removing players from the game");
            game.removeRangeOfPlayers(numPlayers, game.getNumPlayers() );
        }
        else
            System.out.println("JDEBUG: Number of existing players already matches requested amount");
        System.out.println(game);
    }
}
