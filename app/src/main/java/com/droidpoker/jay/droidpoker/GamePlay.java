package com.droidpoker.jay.droidpoker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import poker.TexasHoldem;

public class GamePlay extends AppCompatActivity
{
    private TexasHoldem game;
    private int[] img_res =
    {
        R.mipmap.me, R.mipmap.kels, R.mipmap.trip_mario,
        R.mipmap.joker, R.mipmap.clown, R.mipmap.dragon
    };
    private int[] playerCluster_ids =
    {
            R.id.player1_cluster_ll, R.id.player2_cluster_ll,
            R.id.player3_cluster_ll, R.id.player4_cluster_ll,
            R.id.player5_cluster_ll, R.id.player6_cluster_ll
    };
    private int[] player_iv_ids =
    {
        R.id.player1_iv, R.id.player2_iv, R.id.player3_iv,
        R.id.player4_iv, R.id.player5_iv, R.id.player6_iv
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        game = (TexasHoldem)getIntent().getExtras().getSerializable("game");
        setContentView(R.layout.activity_game_play);
        setPlayerIcons();
    }

    private void setPlayerIcons()
    { // renders player icons at table at start of activity
        System.out.println("DEBUG: calling setPlayerIcons()");
        for(int i=0; i < game.getNumPlayers(); i++)
        {
            LinearLayout playerCluster_ll = (LinearLayout)findViewById( playerCluster_ids[i] );
            playerCluster_ll.setVisibility(View.VISIBLE);

            TextView cashTv = (TextView)playerCluster_ll.findViewById(R.id.player_cash_display_tv);
            String cash =  "$" + String.valueOf( game.getPlayer(i).getCash() );
            cashTv.setText(cash);

            TextView nameTv = ( TextView )playerCluster_ll.findViewById(R.id.player_name_display_tv);
            nameTv.setText(game.getPlayerNames()[i]);

            ImageView player_display_iv = (ImageView)playerCluster_ll
                    .findViewById(player_iv_ids[i]);

            System.out.println("setting " + game.getPlayerNames()[i] + "'s player icon image...");

            new DecodeBitMapFromResourceTask(player_display_iv, getResources())
                    .execute(img_res[i], R.dimen.player_icon_size, R.dimen.player_icon_size);
            player_display_iv.setVisibility(ImageView.VISIBLE);
        }

        // remove player cluster views to minimize memory usage
        if(game.getNumPlayers() < 6)
        {
            for(int i = 0; i < 6-game.getNumPlayers(); i++)
            {
                System.out.println("removing player" + (6-i) + "'s cluster...");
                ((ViewGroup)findViewById(playerCluster_ids[5-i])).removeAllViews();
            }
        }
    }
}


