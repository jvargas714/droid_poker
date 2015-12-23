package com.droidpoker.jay.droidpoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import poker.TexasHoldem;

public class EnterAppActivity extends AppCompatActivity{
    TexasHoldem game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        game = new TexasHoldem(25, 50);
        game.namePlayer("Jay", 0);
        game.namePlayer("Douschist", 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_enter_app_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        /* Handle menu item selected
           and navigate to respective activity
           Holdem class in settings is serialized and sent via the intent
         */
        Intent chosenActivityIntent;
        switch( item.getItemId() ){
            case R.id.settings:
                chosenActivityIntent = new Intent(EnterAppActivity.this, SettingsActivity.class);
                chosenActivityIntent.putExtra("game", game);
                startActivityForResult(chosenActivityIntent, 1);
                break;
            case R.id.help:
                chosenActivityIntent = new Intent(EnterAppActivity.this, HelpActivity.class);
                startActivity(chosenActivityIntent);
                break;
            case R.id.statistics:
                chosenActivityIntent= new Intent(EnterAppActivity.this, StatActivity.class);
                startActivity(chosenActivityIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("TexasHoldem:: onActivityResult called ..... ");
        System.out.println("RequestCode: " + requestCode);
        System.out.println("ResultCode: " + resultCode);
        System.out.println("Result_OK: " + RESULT_OK);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                game = (TexasHoldem)data.getSerializableExtra("gameFromSettings");
                System.out.println("Texas Holdem game recieved from Settings --> " );
                System.out.println(game);
            }
        }
    }
// testing testing 123

}
