package com.example.ai_game_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Button rockButton, paperButton, scissorsButton;
    private ImageView aiChoice, playerChoice;
    private String playerMove, aiMove;
    private int aiScore, playerScore;
    private int rockCount, paperCount, scissorsCount;
    private boolean isHardMode;
    private SharedPreferences prefs;
    private Random random = new Random();
    private View backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initializeViews();
        setOnClickListeners();
    }

    private void initializeViews() {
        rockButton = findViewById(R.id.RockButton);
        paperButton = findViewById(R.id.PaperButton);
        scissorsButton = findViewById(R.id.ScissorsButton);
        aiChoice = findViewById(R.id.AI);
        playerChoice = findViewById(R.id.Player);
        backButton = findViewById(R.id.backButton);
    }

    private void setOnClickListeners() {
        rockButton.setOnClickListener(v -> handleRockButtonClick());
        paperButton.setOnClickListener(v -> handlePaperButtonClick());
        scissorsButton.setOnClickListener(v -> handleScissorsButtonClick());
        backButton.setOnClickListener(v -> finish());
    }

    private void handleRockButtonClick() {
        playerMove = "rock";
        rockCount++;
        playerChoice.setImageResource(R.drawable.rock);
        calculateResult();
    }

    private void handlePaperButtonClick() {
        playerMove = "paper";
        paperCount++;
        playerChoice.setImageResource(R.drawable.paper);
        calculateResult();
    }

    private void handleScissorsButtonClick() {
        playerMove = "scissors";
        scissorsCount++;
        playerChoice.setImageResource(R.drawable.scissor);
        calculateResult();
    }


    private String getAIMove() {
        String[] choices = {"rock", "paper", "scissors"};
        if (isHardMode && (rockCount + paperCount + scissorsCount) >= 10) {
            if (rockCount > 0.75 * (rockCount + paperCount + scissorsCount)) {
                return "paper";
            } else if (paperCount > 0.75 * (rockCount + paperCount + scissorsCount)) {
                return "scissors";
            } else if (scissorsCount > 0.75 * (rockCount + paperCount + scissorsCount)) {
                return "rock";
            } else if (rockCount > 0.5 * (rockCount + paperCount + scissorsCount)) {
                if (new Random().nextInt(2) == 0) {
                    return "paper";
                }
            } else if (paperCount > 0.5 * (rockCount + paperCount + scissorsCount)) {
                if (new Random().nextInt(2) == 0) {
                    return "scissors";
                }
            } else if (scissorsCount > 0.5 * (rockCount + paperCount + scissorsCount)) {
                if (new Random().nextInt(2) == 0) {
                    return "rock";
                }
            }
        }
        return choices[new Random().nextInt(3)];
    }

    private void calculateResult() {
        aiMove = getAIMove();
        setAiChoiceImage();
        showGameResult();
        updatePreferences();
    }

    private void setAiChoiceImage() {
        switch (aiMove) {
            case "rock":
                aiChoice.setImageResource(R.drawable.rock);
                break;
            case "paper":
                aiChoice.setImageResource(R.drawable.paper);
                break;
            case "scissors":
                aiChoice.setImageResource(R.drawable.scissor);
                break;
        }
    }

    private void showGameResult() {
        if (playerMove.equals(aiMove)) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
        } else if ((playerMove.equals("rock") && aiMove.equals("scissors")) ||
                (playerMove.equals("paper") && aiMove.equals("rock")) ||
                (playerMove.equals("scissors") && aiMove.equals("paper"))) {
            playerScore++;
            Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show();
        } else {
            aiScore++;
            Toast.makeText(this, "AI wins!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePreferences() {
        prefs = getSharedPreferences("game", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("rockCount", rockCount);
        editor.putInt("paperCount", paperCount);
        editor.putInt("scissorsCount", scissorsCount);
        editor.apply();
    }
}