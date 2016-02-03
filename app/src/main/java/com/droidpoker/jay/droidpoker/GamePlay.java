package com.droidpoker.jay.droidpoker;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Vector;
import poker.TexasHoldem;

public class GamePlay extends AppCompatActivity
{
    private TexasHoldem game;
    private Vector<Drawable> playerImgs = new Vector<>(6);
    private Resources res;

    private int[] img_res = {R.mipmap.me, R.mipmap.kels, R.mipmap.trip_mario,
                             R.mipmap.joker, R.mipmap.clown, R.mipmap.dragon};

    private int[] playerClusters = {R.id.player1_cluster_ll, R.id.player2_cluster_ll,
                                    R.id.player3_cluster_ll, R.id.player4_cluster_ll,
                                    R.id.player5_cluster_ll, R.id.player6_cluster_ll};

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
            LinearLayout playerCluster_ll = (LinearLayout)findViewById(playerClusters[i]);
            playerCluster_ll.setVisibility(View.VISIBLE);

            TextView cashTv = (TextView)playerCluster_ll.findViewById(R.id.player_cash_display_tv);
            String cash =  "$" + String.valueOf(game.getPlayer(i).getCash());
            cashTv.setText(cash);

            TextView nameTv = (TextView)playerCluster_ll.findViewById(R.id.player_name_display_tv);
            nameTv.setText( game.getPlayerNames()[i] );
        }
    }
}


