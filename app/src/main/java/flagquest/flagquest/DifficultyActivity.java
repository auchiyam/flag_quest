package flagquest.flagquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//Activity to set the difficulty
public class DifficultyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        configureButtons();
    }

    private void configureButtons() {
        Button easy = (Button) findViewById(R.id.difficulty_easy);
        Button medium = (Button) findViewById(R.id.difficulty_medium);
        Button hard = (Button) findViewById(R.id.difficulty_hard);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameState.difficulty = 0;
                startActivity(new Intent(DifficultyActivity.this, GameActivity.class));
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameState.difficulty = 1;
                startActivity(new Intent(DifficultyActivity.this, GameActivity.class));
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameState.difficulty = 2;
                startActivity(new Intent(DifficultyActivity.this, GameActivity.class));
            }
        });
    }

}
