package com.example.ai_game_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    private TextView rockCountView, paperCountView, scissorsCountView, totalGamesView;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        rockCountView = findViewById(R.id.RockTextView);
        paperCountView = findViewById(R.id.PaperTextView);
        scissorsCountView = findViewById(R.id.ScissorsTextView);
        totalGamesView = findViewById(R.id.TotalGamesTextView);
        backButton = findViewById(R.id.backButton);

        // get the statistics from the shared preferences
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        int rockCount = prefs.getInt("rockCount", 0);
        int paperCount = prefs.getInt("paperCount", 0);
        int scissorsCount = prefs.getInt("scissorsCount", 0);
        int totalGames = rockCount + paperCount + scissorsCount;

        // Calculate the percentage of each move.
        double rockPercent = (double) rockCount / totalGames * 100;
        double paperPercent = (double) paperCount / totalGames * 100;
        double scissorsPercent = (double) scissorsCount / totalGames * 100;

        // show the statistics in the text views
        rockCountView.setText(String.format("Rock: %d (%.2f%%)", rockCount, rockPercent));
        paperCountView.setText(String.format("Paper: %d (%.2f%%)", paperCount, paperPercent));
        scissorsCountView.setText(String.format("Scissors: %d (%.2f%%)", scissorsCount, scissorsPercent));
        totalGamesView.setText(String.format("Total Games: %d", totalGames));

        // Implement the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}