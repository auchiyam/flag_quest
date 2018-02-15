package flagquest.flagquest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreDBHandler extends SQLiteOpenHelper {
    private static final int VERSION = 3;
    private static final String NAME = "flag_quest";

    private static final String table_name = "score";

    private static final String key_id = "id";
    private static final String key_date = "date";
    private static final String key_name = "name";
    private static final String key_score = "score";
    private static final String key_difficulty = "difficulty";

    public ScoreDBHandler(Context context) { super(context, NAME, null, VERSION); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String initialize_table = ""
                + "CREATE TABLE " + table_name + "("
                + key_id + " integer primary key, "
                + key_difficulty + " integer, "
                + key_name + " text, "
                + key_score + " integer, "
                + key_date + " date" + ")";
        sqLiteDatabase.execSQL(initialize_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(sqLiteDatabase);
    }

    //CREATE
    public void addScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(key_name, score.getName());
        v.put(key_date, score.getDate().toString());
        v.put(key_score, score.getScore());
        v.put(key_difficulty, score.getDifficulty());

        db.insert(table_name, null, v);
        db.close();
    }

    //READ
    public Score getScore(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(table_name, new String[] { key_id, key_name, key_score, key_date, key_difficulty}, key_id + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        Score s;
        if (c != null) {
            c.moveToFirst();
            s = new Score(c.getString(0), c.getInt(1), new Date(c.getString(2)), c.getInt(3));
        }
        else {
            throw new IndexOutOfBoundsException();
        }

        return s;
    }

    //Get all scores for that difficulty
    public List<Score> getAllScore(int difficulty) {
        List<Score> s = new ArrayList<Score>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor d = db.rawQuery("select * from score", null);
        String[] a = d.getColumnNames();
        for (int u = 0; u < a.length; u++) {
            System.out.println(a[u]);
        }

        Cursor c = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = ?", table_name, key_difficulty), new String[] {String.valueOf(difficulty)});

        if (c.moveToFirst()) {
            do {
                Score sc = new Score(c.getString(0), c.getInt(1), new Date(c.getString(2)), difficulty);
                s.add(sc);
            } while (c.moveToNext());
        }
        return s;
    }

    public Score getHighscore(int difficulty) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("" +
                        "SELECT " + key_name + ", " + key_score + ", " + key_date + " " +
                        "FROM " + table_name + ", (SELECT Max(" + key_score + ") as hs FROM " + table_name + ") as Highscore " +
                        "WHERE Highscore.hs = " + table_name + "." + key_score + " AND " + table_name + "." + key_score + " = " + difficulty
                        , null);

        Score s = null;
        if (c != null) {
            c.moveToFirst();
            s = new Score(c.getString(0), c.getInt(1), new Date(c.getString(2)), difficulty);
        }
        else {
            throw new IndexOutOfBoundsException();
        }

        return s;
    }
}
