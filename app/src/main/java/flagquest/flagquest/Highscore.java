package flagquest.flagquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Highscore extends AppCompatActivity {
    private List<Score> scoreboard;
    ScoreDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        db = new ScoreDBHandler(Highscore.this);

        scoreboard = db.getAllScore(0);

        DisplayScoreBoard();

        configureButtons();
    }

    private void configureButtons() {
        configureEasy();
        configureMedium();
        configureHard();
    }

    private void configureEasy() {
        Button easy = (Button) findViewById(R.id.highscore_easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreboard = db.getAllScore(0);
                DisplayScoreBoard();
            }
        });
    }

    private void configureMedium() {
        Button medium = (Button) findViewById(R.id.highscore_medium);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreboard = db.getAllScore(1);
                DisplayScoreBoard();
            }
        });
    }

    private void configureHard() {
        Button hard = (Button) findViewById(R.id.highscore_hard);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreboard = db.getAllScore(2);
                DisplayScoreBoard();
            }
        });
    }

    private void DisplayScoreBoard() {
        ListView l = (ListView) findViewById(R.id.scores);
        ScoreListAdapter adapter = new ScoreListAdapter(Highscore.this, scoreboard);
        l.setAdapter(adapter);
    }
}
