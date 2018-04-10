package flagquest.flagquest;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;

//Tracks the game currently playing
public class GameState {
    public static int score;
    public static int difficulty;
    public static int question_num;
    public static Random random;
    public static ArrayList<Integer> used;
    public static Drawable flag_used;
    public static boolean[][] hidden;
}
