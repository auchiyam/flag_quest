package flagquest.flagquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        startActivity(new Intent(MainActivity.this, MainPage.class));
        finish();

        GameState.random = new Random();
    }
}
