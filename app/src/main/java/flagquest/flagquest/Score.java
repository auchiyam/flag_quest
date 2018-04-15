package flagquest.flagquest;

import android.support.annotation.NonNull;

import java.util.Date;

//Class used to display various info for highscores list
public class Score implements Comparable<Score> {
    private String name; //Name of player
    private Date date; //Date score achieved
    private int score; //Score
    private int difficulty; //0 = Easy, 1 = Medium, 2 = Hard

    public Score(String n, int s, Date d, int df) {
        name = n;
        date = d;
        score = s;
        difficulty = df;
    }


    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    public int getDifficulty() {
        return difficulty;
    }


    @Override
    public int compareTo(@NonNull Score i) {
        if (difficulty < i.getDifficulty()) {
            return -1;
        }
        else if (difficulty > i.getDifficulty()) {
            return 1;
        }
        else {
            return score - i.getScore();
        }
    }
}
