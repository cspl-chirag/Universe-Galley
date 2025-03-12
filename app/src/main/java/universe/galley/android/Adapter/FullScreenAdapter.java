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
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import universe.galley.android.R;

public class FullScreenAdapter extends RecyclerView.Adapter<FullScreenAdapter.ViewHolder> {

    Context context;
    ArrayList<String> mediaList;

    public FullScreenAdapter(Context context, ArrayList<String> mediaList) {
        this.context = context;
        this.mediaList = mediaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fullscreen_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String filePath = mediaList.get(position);

        if (filePath.endsWith(".mp4") || filePath.endsWith(".mkv")) {
            holder.imageView.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoURI(Uri.parse(filePath));
            holder.videoView.start();
        } else {
            // Show Image
            holder.videoView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(filePath)
                    .into(holder.imageView);
        }

        holder.btnDelete.setOnClickListener(v -> {
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

        holder.btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mediaList.get(position))));
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
        });
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        VideoView videoView;
        ImageView btnDelete;
        ImageView btnShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fullScreenImage);
            videoView = itemView.findViewById(R.id.fullScreenVideo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnShare = itemView.findViewById(R.id.btnShare);
        }
    }
}
