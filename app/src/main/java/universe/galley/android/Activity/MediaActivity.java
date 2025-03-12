package universe.galley.android.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import universe.galley.android.Adapter.MediaAdapter;
import universe.galley.android.R;

public class MediaActivity extends AppCompatActivity {
    RecyclerView mediaRecyclerView;
    ArrayList<String> mediaList;
    MediaAdapter mediaAdapter;

//    ViewPager viewPager;
//    ArrayList<String> mediaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        mediaRecyclerView = findViewById(R.id.mediaRecyclerView);
        mediaRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // Get the media list from intent
        mediaList = getIntent().getStringArrayListExtra("mediaList");

        if (mediaList != null && !mediaList.isEmpty()) {
            mediaAdapter = new MediaAdapter(this, mediaList);
            mediaRecyclerView.setAdapter(mediaAdapter);
        }

//        viewPager = findViewById(R.id.viewPager);
//        mediaList = getIntent().getStringArrayListExtra("mediaList");
//
//        MediaAdapter adapter = new MediaAdapter(this, mediaList);
//        viewPager.setAdapter(adapter);
    }
}