package flagquest.flagquest;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreListAdapter extends BaseAdapter {
    Context context;
    List<Score> data;
    private static LayoutInflater inflater;

    public ScoreListAdapter(Context c, List<Score> d) {
        context = c;
        data = d;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.size() < i ? data.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null) {
            vi = inflater.inflate(R.layout.score_list, null);
        }
        TextView score = (TextView)vi.findViewById(R.id.highscore_score);
        TextView date = (TextView)vi.findViewById(R.id.highscore_date);
        TextView name = (TextView)vi.findViewById(R.id.highscore_name);
        TextView difficulty = (TextView)vi.findViewById(R.id.highscore_difficulty);

        score.setText(String.valueOf(data.get(i).getScore()));
        date.setText(data.get(i).getDate().toString());
        name.setText(data.get(i).getName());

        String diff = "Easy";
        switch (data.get(i).getDifficulty()) {
            case 1:
                diff = "Medium";
                break;
            case 2:
                diff = "Hard";
                break;
        }

        difficulty.setText(diff);
        return vi;
    }
}
