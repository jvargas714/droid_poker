package com.droidpoker.jay.droidpoker;
/**
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.Vector;
import poker.TexasHoldem;


public class SettingsActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    protected Spinner       numPlayers_spinner;
    protected TexasHoldem   game;
    protected EditText      enter_cash_et;
    protected int           desiredNumPlayers;
    protected boolean       numPlayersChanged;
    protected boolean       entryCashChanged;
    protected int           startingCash;
    protected LinearLayout  player_names_ll;
    protected int[] player_name_item_res =
            {
                R.id.player1_item, R.id.player2_item, R.id.player3_item,
                R.id.player4_item, R.id.player5_item, R.id.player6_item
            };
    private int[] img_res =
            {
                R.mipmap.me, R.mipmap.kels, R.mipmap.trip_mario,
                R.mipmap.joker, R.mipmap.clown, R.mipmap.dragon
            };
    public Vector<Drawable> playerImgs = new Vector<>(6);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        game = (TexasHoldem)bundle.getSerializable("game");

        assert game != null;
        Log.d("JDEBUG", "player 1 name --> " + game.getPlayer(0).getName());
        Log.d("JDEBUG", "player 2 name --> " + game.getPlayer(1).getName());

        numPlayers_spinner = (Spinner) findViewById(R.id.num_players_spinner);

        ArrayAdapter<CharSequence> charAdapter =
                ArrayAdapter.createFromResource(this, R.array.num_players_array,
                        android.R.layout.simple_spinner_item);

        charAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numPlayers_spinner.setAdapter(charAdapter);
        numPlayers_spinner.setOnItemSelectedListener(this);

        // render 2 player list view
        player_names_ll = (LinearLayout)findViewById(R.id.player_names_ll);
        String[] names = {game.getPlayer(0).getName(), game.getPlayer(1).getName() };

        // intialize game parametes and Drawable
        desiredNumPlayers = 2;
        numPlayersChanged = false;
        entryCashChanged = false;
        startingCash = 1500;

//        Runnable decodeImgs = new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                // load and decode player icon imgs at activity start up
//                for (int resid : img_res)
//                {
//                    Drawable icon = getResources().getDrawable(resid);
//                    playerImgs.add(icon);
//                }
//            }
//        };
//        decodeImgs.run();
        renderNameList(player_names_ll, names);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // number of players selected by user
        Log.d("JDEBUG: ", " ITEM SELECTED at position: " + position);
        SpinnerAdapter sa = numPlayers_spinner.getAdapter();
        desiredNumPlayers = Integer.parseInt( (String)sa.getItem( position ) );
        System.out.println("JDEBUG::dataPt: " + desiredNumPlayers + ", rendering name listview...");
        setNumPlayers(desiredNumPlayers, game);
        renderNameList( player_names_ll, game.getPlayerNames() );
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }


    private static void setNumPlayers(int numPlayers, TexasHoldem game)
    {
        /**
        sets number of players in game obj
        set number of players
         */
        System.out.println("SetNumPlayers called...");
        if(game.getNumPlayers() < numPlayers)
        {
            int num_additional_players = numPlayers - game.getNumPlayers();
            Integer n = game.getNumPlayers() + 1;
            System.out.println("JDEBUG:: adding players now....");
            System.out.println("JDEBUG:: number of additional players = " + num_additional_players);

            for(int i=0; i<num_additional_players; i++)
            {
                System.out.println("JDEBUG::" + num_additional_players + "Players entering the game!!!!");
                game.enterGame("Player " + n.toString());
                n++;
            }
        }
        else if(game.getNumPlayers() > numPlayers)
        {
            // need to remove extra players to the desired amount of players
            System.out.println("JDEBUG:: removing players from the game");
            game.removeRangeOfPlayers(numPlayers, game.getNumPlayers() );
        }
        else
            System.out.println("JDEBUG: Number of existing players already matches requested amount");
        System.out.println(game);
    }

    @Override
    public void onBackPressed()
    {
        // set amount of cash starting with
        enter_cash_et = (EditText)findViewById(R.id.starting_cash_etxt);
        int starting_cash = Integer.parseInt(enter_cash_et.getText().toString() );
        game.setEnterCash(starting_cash);

        // copy names in TextEdit player name elements
        for(int i = 0; i < game.getNumPlayers(); i++)
        {
            EditText name_et = (EditText)findViewById(player_name_item_res[i]).
                    findViewById(R.id.player_name_item_et);
            String name = name_et.getText().toString();
            System.out.println("Adding name to game object--> old name: " + game.getPlayerNames()[i]
                    +" and new name: " + name );
            game.getPlayer(i).namePlayer(name);
        }

        // send modifed texasholdem class back to EnterAppActivity
        Intent intent = new Intent(SettingsActivity.this, EnterAppActivity.class);
        intent.putExtra("gameFromSettings", game);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }


    private void renderNameList(LinearLayout ll, String[] names)
    {
        /** when user selects how many user are to play in the game
         *  the list view will automatically update
         *
         *  a new adapter is created and the list is populated and
         *  player pictures are applied
         */

        System.out.println("RenderNameList:: attempting to render name list..");
        System.out.println("Length of names: " + names.length);

        for(int i = 0 ; i < names.length; i++)
        {
            ll.findViewById(player_name_item_res[i]).setVisibility(View.VISIBLE);
            ImageView iv = (ImageView)ll.findViewById( player_name_item_res[i] ).
                    findViewById( R.id.player_icon_imgview );
//            iv.setImageDrawable(playerImgs.get(i));
            new DecodeBitMapFromResourceTask( iv, getResources())
                    .execute(img_res[i], R.dimen.player_icon_size, R.dimen.player_icon_size_settings);

            EditText et = (EditText)ll.findViewById(player_name_item_res[i]).
                    findViewById(R.id.player_name_item_et);
            et.setText( names[i] );
            iv.setVisibility(View.VISIBLE);
        }
        // hide views not in use
        int numToRemove = 6 - names.length;
        for(int i = 0; i < numToRemove; i++)
        {
            ll.findViewById(player_name_item_res[5-i]).setVisibility(View.INVISIBLE);
        }
    }
}
