package universe.galley.android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import universe.galley.android.R;

public class MediaAdapter extends androidx.viewpager.widget.PagerAdapter {

    Context context;
    ArrayList<String> mediaList;

    public MediaAdapter(Context context, ArrayList<String> mediaList) {
        this.context = context;
        this.mediaList = mediaList;
    }

    @Override
    public int getCount() {
        return mediaList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        ImageView btnDelete = view.findViewById(R.id.btnDelete);
        ImageView btnShare = view.findViewById(R.id.btnShare);

        Glide.with(context).load(mediaList.get(position)).into(imageView);

        btnDelete.setOnClickListener(v -> {
            File file = new File(mediaList.get(position));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String selection = MediaStore.Images.Media.DATA + "=?";
                String[] selectionArgs = new String[]{file.getAbsolutePath()};
                int rowsDeleted = context.getContentResolver().delete(uri, selection, selectionArgs);
                if (rowsDeleted > 0) {
                    Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show();
                    mediaList.remove(position);
                    notifyDataSetChanged();
                }
            } else {
                if (file.exists()) {
                    file.delete();
                    mediaList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mediaList.get(position))));
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}