package universe.galley.android.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import universe.galley.android.Adapter.FullScreenAdapter;
import universe.galley.android.R;

public class FullScreenActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    ArrayList<String> mediaList;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        viewPager = findViewById(R.id.viewPager);

        // Get data from Intent
        mediaList = getIntent().getStringArrayListExtra("mediaList");
        position = getIntent().getIntExtra("position", 0);

        // Set ViewPager Adapter
        FullScreenAdapter adapter = new FullScreenAdapter(this, mediaList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);
    }
}