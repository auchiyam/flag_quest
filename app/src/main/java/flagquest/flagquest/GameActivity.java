package flagquest.flagquest;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

//Defines activity for basic fill in the blank questions
public class GameActivity extends AppCompatActivity {
    ScoreDBHandler db;
    CountDownTimer t;
    String correct;
    int reveal_iteration = 0;

    final int total_questions = 10;
    final int total_time = 60000;
    final int refresh_rate = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        db = new ScoreDBHandler(GameActivity.this);

        configureGame();
        GameState.question_num++;
        start_timer();
    }

    private void configureGame() {
        GameState.hidden = new boolean[50][30];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 30; j++) {
                GameState.hidden[i][j] = false;
            }
        }

        //Get the flag that will be used for the question
        setFlag();

        //Configure the hint prompt
        String hint = generateHint(correct);
        TextView hinttext = (TextView)findViewById(R.id.hint_blank);
        hinttext.setText(hint);

        //Configure the submit button
        Button subm = (Button)findViewById(R.id.submit_answer_blank);
        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ans = (EditText)findViewById(R.id.answer_blank);
                TextView timer = (TextView)findViewById(R.id.timer_blank);

                String answer = ans.getText().toString();

                boolean is_correct = false;

                //Empty answer
                if (answer.equals("")) {
                    ShowSnackBar("Please enter an answer");
                //Wrong answer
                } else if (!answer.toLowerCase().equals(correct.toLowerCase())) {
                    ShowSnackBar("Wrong answer.  Try again");

                    GameState.score -= 5;
                //Right answer
                } else {
                    int scr = Integer.parseInt(timer.getText().toString());

                    GameState.score += scr * (GameState.difficulty + 1);

                    is_correct = true;
                }

                //keep going until the user gets it correct or the timer runs out
                if (is_correct) {
                    nextQuestion(is_correct);
                }
            }
        });
    }

    //returns array of 2 strings, the first string being the country name for the flag and second string being the relative path to the flag file for drawing
    private void setFlag() {
        TypedArray flags = getResources().obtainTypedArray(R.array.flags);
        TypedArray ans = getResources().obtainTypedArray(R.array.answers);

        int rand;

        do {
            rand = GameState.random.nextInt(flags.length());
            if (flags.length() == GameState.used.size()) {
                break;
            }
        //keep getting random flags until either the random number is something not used or the used array is greater than or equal to the amount of total flags available
        } while (GameState.used.contains(rand));

        //if we used every flags we have, reset the used array to reuse everything
        if (flags.length() == GameState.used.size()) {
            GameState.used = new ArrayList<Integer>();
        }

        GameState.used.add(rand);

        GameState.flag_used = flags.getDrawable(rand);
        correct = ans.getString(rand);
    }

    private void start_timer() {
        t = new CountDownTimer(total_time, 1000) {
            public void onTick(long time_left) {
                TextView left = (TextView)findViewById(R.id.timer_blank);
                left.setText("" + (time_left / 1000));

                if ((time_left / 1000) * 1000 % refresh_rate == 0) {
                    adjustFlag();
                }
            }

            public void onFinish() {
                nextQuestion(false);
            }
        }.start();
    }

    private void nextQuestion(boolean is_correct) {
        t.cancel();
        if (GameState.question_num >= total_questions) {
            //temporary, skips result page and records score and returns to the main page
            Score s = new Score("Zeus", GameState.score, Calendar.getInstance().getTime(), GameState.difficulty);

            db.addScore(s);

            startActivity(new Intent(GameActivity.this, MainPage.class));
            return;
        }

        startActivity(new Intent(GameActivity.this, GameActivity.class));
    }

    private String generateHint(String answer) {
        double ratio = 1;

        String hint = answer;

        //depending on the difficulty, the ratio of text hidden changes
        switch (GameState.difficulty) {
            case 0:
                ratio = .5;
                break;
            case 1:
                ratio = .75;
                break;
            case 2:
                ratio = .9;
                break;
        }

        //determine how many letter should be hidden
        int total = (int)(answer.length() * ratio) + 1;
        int curr = 0;
        int[] used = new int[answer.length()];

        //Initialize used
        for (int i = 0; i < used.length; i++) {
            used[i] = 0;
        }

        int rand;
        //keep replacing letters with _ <total> times to hide the hint
        for (int i = 0; i < total; i++) {

            do {
                rand = GameState.random.nextInt(answer.length());
                //keep getting random letter until either the random number is something not used
            } while (used[rand] == 1);

            hint = hint.substring(0, rand) + "_" + hint.substring(rand+1);

            used[i] = 1;
        }

        //Add space between every letter to make the underscore easier to count
        hint = pad(hint);

        return hint;
    }

    private void adjustFlag() {
        int total = GameState.hidden.length * GameState.hidden[0].length / (total_time / refresh_rate);

        if (reveal_iteration == (total_time / refresh_rate)) {
            for (int i = 0; i < GameState.hidden.length; i++) {
                for (int j = 0; j < GameState.hidden[i].length; j++) {
                    if (!GameState.hidden[i][j]) {
                        GameState.hidden[i][j] = true;
                    }
                }
            }
        }

        for (int i = 0; i < total; i++) {
            int rx;
            int ry;

            do {
                rx = GameState.random.nextInt(GameState.hidden.length);
                ry = GameState.random.nextInt(GameState.hidden[0].length);
            } while (GameState.hidden[rx][ry]);

            GameState.hidden[rx][ry] = true;
        }

        int tot = 0;
        for (int i = 0; i < GameState.hidden.length; i++) {
            for (int j = 0; j < GameState.hidden[i].length; j++) {
                if (GameState.hidden[i][j]) {
                    tot++;
                }
            }
        }

        FlagView v = findViewById(R.id.flagview_blank);
        v.invalidate();
        reveal_iteration++;
    }

    private void ShowSnackBar(String s) {
        Snackbar snack = Snackbar.make(findViewById(R.id.game_blank), s, Snackbar.LENGTH_SHORT);
        View view = snack.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }

    private String pad(String s) {
        String newText = "";
        for (int i = 0; i < s.length(); i++) {
            newText += s.charAt(i) + " ";
        }
        return newText;
    }

    //Disallow the back button press during the game
    @Override
    public void onBackPressed() {
        return;
    }
}
