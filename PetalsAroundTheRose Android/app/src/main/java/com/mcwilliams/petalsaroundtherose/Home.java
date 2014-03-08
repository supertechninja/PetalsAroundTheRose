package com.mcwilliams.petalsaroundtherose;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.android.gms.ads.*;

public class Home extends ActionBarActivity {

    List<Dice> diceList = new ArrayList<Dice>();
    List<Dice> diceToDisplay = new ArrayList<Dice>();
    ImageView ivDice1;
    ImageView ivDice2;
    ImageView ivDice3;
    ImageView ivDice4;
    ImageView ivDice5;
    ImageView ivDice6;
    int answer;
    EditText etGuess;
    TextView tvAnswer;
    TextView tvRightOrWrong;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Dice dice1 = new Dice("One", 1, R.drawable.dice);
        Dice dice2 = new Dice("Two", 2, R.drawable.dice3);
        Dice dice3 = new Dice("Three", 3, R.drawable.three7, 2);
        Dice dice4 = new Dice("Four", 4, R.drawable.dice4);
        Dice dice5 = new Dice("Five", 5, R.drawable.dice1, 4);
        Dice dice6 = new Dice("Six", 6, R.drawable.dice6);
        //
        diceList.add(dice1);
        diceList.add(dice2);
        diceList.add(dice3);
        diceList.add(dice4);
        diceList.add(dice5);
        diceList.add(dice6);
        //
        ivDice1 = (ImageView) findViewById(R.id.dice1);
        ivDice2 = (ImageView) findViewById(R.id.dice2);
        ivDice3 = (ImageView) findViewById(R.id.dice3);
        ivDice4 = (ImageView) findViewById(R.id.dice4);
        ivDice5 = (ImageView) findViewById(R.id.dice5);
        ivDice6 = (ImageView) findViewById(R.id.dice6);
        etGuess = (EditText) findViewById(R.id.etGuess);
        tvAnswer = (TextView) findViewById(R.id.tvAnswer);
        tvRightOrWrong = (TextView) findViewById(R.id.tvRightOrWrong);
        //
        etGuess.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    calculateTotal();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etGuess.getWindowToken(), 0);
                }
                return false;
            }
        });
        randomizeDice();

        if (!isFirstTime()) {
            showRules();
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-8930204067777857/1044663033");
        LinearLayout llAdView = (LinearLayout) findViewById(R.id.llAdview);
        llAdView.addView(adView);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.info_button:
               showRules();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRollDiceClicked(View view) {
        randomizeDice();
    }

    public void randomizeDice() {
        final Animation flipin = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        tvRightOrWrong.setVisibility(View.GONE);
        diceToDisplay.clear();
        tvAnswer.setText("");
        etGuess.setText("");
        for (int i = 0; i < 6; i++) {
            int min = 0;
            int num_top = diceList.size();
            Random r2 = new Random();
            int randomNum2 = r2.nextInt(num_top) + min;
            diceToDisplay.add(diceList.get(randomNum2));
        }
        ivDice1.setImageResource(diceToDisplay.get(0).getDiceImageResource());
        findViewById(R.id.dice1).startAnimation(flipin);
        ivDice2.setImageResource(diceToDisplay.get(1).getDiceImageResource());
        findViewById(R.id.dice2).startAnimation(flipin);
        ivDice3.setImageResource(diceToDisplay.get(2).getDiceImageResource());
        findViewById(R.id.dice3).startAnimation(flipin);
        ivDice4.setImageResource(diceToDisplay.get(3).getDiceImageResource());
        findViewById(R.id.dice4).startAnimation(flipin);
        ivDice5.setImageResource(diceToDisplay.get(4).getDiceImageResource());
        findViewById(R.id.dice5).startAnimation(flipin);
        ivDice6.setImageResource(diceToDisplay.get(5).getDiceImageResource());
        findViewById(R.id.dice6).startAnimation(flipin);
    }

    public void calculateTotal() {
        answer = 0;
        for (Dice dice : diceToDisplay) {
            if (dice.getDiceValue() == 3 || dice.getDiceValue() == 5) {
                answer += dice.getPetalsAroundTheRose();
            }
        }
        tvAnswer.setText(String.valueOf(answer));
        compare();
    }

    public void compare() {
        if (etGuess.getText().toString().equals(tvAnswer.getText())) {
            tvRightOrWrong.setText("You're right! \nKeep the solution a secret");
        } else {
            tvRightOrWrong.setText("You're wrong, keep trying!");
        }
        tvRightOrWrong.setVisibility(View.VISIBLE);
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();

            return false;
        }
        return ranBefore;
    }

    public void showRules(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("1. The name of the game is important \n 2. Every answer is either Zero or an even number \n 3. Anyone that figures it out may not give away the reasoning");
        builder.setTitle("Rules of the Game");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        adView.resume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }
}
