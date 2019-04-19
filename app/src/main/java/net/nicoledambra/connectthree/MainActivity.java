package net.nicoledambra.connectthree;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // using int, in case there is ever a want to increase player count
    // 0 = yellow; 1 = red; 2 = empty
    int activePlayer = 0;
    boolean gameActive = true;

    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7},{2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-3000);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(3000).setDuration(300);

            for (int[] winningPos : winningPositions) {
                if (gameState[winningPos[0]] == gameState[winningPos[1]] && gameState[winningPos[1]] == gameState[winningPos[2]] && gameState[winningPos[0]] != 2) {
                    //we have a winner!
                    String winner;
                    gameActive = false;
                    //remember we already switched the active player above
                    if (activePlayer == 1)
                        winner = "Yellow";
                    else
                        winner = "Red";

                    Button mPlayAgainButton = findViewById(R.id.playAgainButton);
                    TextView mWinnerTextView = findViewById(R.id.winnerTextView);
                    mWinnerTextView.setText(winner+" has won!");

                    mPlayAgainButton.setVisibility(View.VISIBLE);
                    mWinnerTextView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if(!gameActive)
                Toast.makeText(this, "The game is over!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Invalid choice", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAgain(View view) {
        Button mPlayAgainButton = findViewById(R.id.playAgainButton);
        TextView mWinnerTextView = findViewById(R.id.winnerTextView);
        mPlayAgainButton.setVisibility(View.INVISIBLE);
        mWinnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
            gameState[i] = 2;
        }

        activePlayer = 0;
        gameActive = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


}
