package flagquest.flagquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Random;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        configureButtons();
    }

    private void configureButtons() {
        configureSetting();
        configurePlay();
        configureHighscore();
        configureExit();
    }
	
	//Main button used to start game
    private void configurePlay() {
        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainPage.this, GameActivity.class));
                }
        });

        GameState.score = 0;
        GameState.difficulty = 0;
        GameState.question_num = 0;
        GameState.random = new Random();
        GameState.used = new ArrayList<Integer>();
        GameState.hidden = new boolean[50][30];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 30; j++) {
                GameState.hidden[i][j] = false;
            }
        }

    }
	
	//Button used for global system settings
    private void configureSetting() {
        ImageButton setting = (ImageButton) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, Setting.class));
            }
        });
    }

	//Button used to go to highscores
    private void configureHighscore() {
        Button highscore = (Button) findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, Highscore.class));
            }
        });
    }

	//Button for exiting out of the game
    private void configureExit() {
        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //When back is pressed in the main page, it exists the game
    @Override
    public void onBackPressed() {
        finish();
    }
}
