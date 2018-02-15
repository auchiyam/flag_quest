package flagquest.flagquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class GameActivity extends AppCompatActivity {
    ScoreDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        db = new ScoreDBHandler(GameActivity.this);

        configureTesting();
    }

    private void configureTesting() {
        Button medium = (Button) findViewById(R.id.submit);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText t1 = (EditText) findViewById(R.id.score_test);
                EditText t2 = (EditText) findViewById(R.id.name_test);
                EditText t3 = (EditText) findViewById(R.id.difficulty_test);

                int score = Integer.parseInt(t1.getText().toString());
                String difficulty = t3.getText().toString();
                String name = t2.getText().toString();

                int diffNum = 0;
                switch (difficulty) {
                    case "Easy":
                        diffNum = 0;
                        break;
                    case "Medium":
                        diffNum = 1;
                        break;
                    case "Hard":
                        diffNum = 2;
                        break;
                }

                Score s = new Score(name, score, Calendar.getInstance().getTime(), diffNum);

                db.addScore(s);
            }
        });
    }
}
