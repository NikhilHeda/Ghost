package com.google.engedu.ghost;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends Activity implements View.OnClickListener {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    private Button challenge, reset;
    private TextView ghostText, gameStatus;
    private String wordFragment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
            //dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        initialize();

        onStart(null);

    }

    private void initialize() {
        ghostText = (TextView) findViewById(R.id.tvGhostText);
        gameStatus = (TextView) findViewById(R.id.tvGameStatus);
        challenge = (Button) findViewById(R.id.bChallenge);
        reset = (Button) findViewById(R.id.bReset);

        challenge.setOnClickListener(this);
        reset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bChallenge:
                challenge();
                break;
            case R.id.bReset:
                onStart(null);
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode >= 29 && keyCode <= 54) {
            char c = (char) event.getUnicodeChar();
            wordFragment = wordFragment.concat(Character.toString(c));
            ghostText.setText(wordFragment);
            computerTurn();
            if (dictionary.isWord(wordFragment)) {
                gameStatus.setText("Valid Word");
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void challenge() {
        if (wordFragment.length() >= 4 && dictionary.isWord(wordFragment))
            gameStatus.setText("Player Wins !");
        else if (dictionary.getAnyWordStartingWith(wordFragment).equals(""))
            gameStatus.setText("Player Wins !");
        else {
            wordFragment = wordFragment.concat(dictionary.getAnyWordStartingWith(wordFragment));
            ghostText.setText(wordFragment);
            gameStatus.setText("Computer Wins !");
        }
    }

    /*
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        wordFragment = "";
        ghostText.setText("");
        if (userTurn) {
            gameStatus.setText(USER_TURN);
        } else {
            gameStatus.setText(COMPUTER_TURN);
            //computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        // Do computer turn stuff then make it the user's turn again
        if (wordFragment.length() >= 4 && dictionary.isWord(wordFragment))
            gameStatus.setText("Computer Wins !");
        else if (dictionary.getAnyWordStartingWith(wordFragment).equals(""))
            gameStatus.setText("Computer Wins !");
        else {
            wordFragment = wordFragment.concat(Character.toString(dictionary.getAnyWordStartingWith(wordFragment).charAt(0)));
            ghostText.setText(wordFragment);
            userTurn = true;
            gameStatus.setText(USER_TURN);
        }
    }

}
