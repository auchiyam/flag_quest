package flagquest.flagquest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.view.View;

public class FlagView extends View {
    RectF[][] cover;
    Paint color;

    int widthNum = 300;
    int heightNum = 100;

    public FlagView(Context context) {
        super(context);

        init(null);
    }

    public FlagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public FlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    public void init(@Nullable AttributeSet attrs) {
        cover = new RectF[GameState.hidden.length][GameState.hidden[0].length];
        for (int i = 0; i < cover.length; i++) {
            for (int j = 0; j < cover[i].length; j++) {
                cover[i][j] = new RectF();
            }
        }

        color = new Paint();
        color.setColor(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect area = canvas.getClipBounds();

        widthNum = GameState.hidden.length;
        heightNum = GameState.hidden[0].length;

        Drawable flag = GameState.flag_used;
        flag.setBounds(area);
        flag.draw(canvas);

        int startx = area.left;
        int starty = area.top;
        double iterx = (double)(area.right - area.left) / widthNum;
        double itery = (double)(area.bottom - area.top) / heightNum;
        for (int i = 0; i < widthNum; i++) {
            for (int j = 0; j < heightNum; j++) {
                if (GameState.hidden[i][j]) {
                    continue;
                }
                cover[i][j].set(new RectF((float)(startx + iterx * i), (float)(starty + itery * j), (float)(startx + iterx * (i+1)), (float)(starty + itery * (j+1))));
                canvas.drawRect(cover[i][j], color);
            }
        }
    }
}
