package universe.galley.android.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import universe.galley.android.Adapter.MediaAdapter;
import universe.galley.android.R;

public class MediaActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<String> mediaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        viewPager = findViewById(R.id.viewPager);
        mediaList = getIntent().getStringArrayListExtra("mediaList");

        MediaAdapter adapter = new MediaAdapter(this, mediaList);
        viewPager.setAdapter(adapter);
    }
}