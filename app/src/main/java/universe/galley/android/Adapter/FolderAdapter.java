package universe.galley.android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import universe.galley.android.Activity.MediaActivity;
import universe.galley.android.R;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    Context context;
    ArrayList<String> folderList;
    HashMap<String, ArrayList<String>> folderMap;

    public FolderAdapter(Context context, ArrayList<String> folderList, HashMap<String, ArrayList<String>> folderMap) {
        this.context = context;
        this.folderList = folderList;
        this.folderMap = folderMap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String folderName = folderList.get(position);
        holder.folderName.setText(folderName);
        int totalMedia = folderMap.get(folderName).size();
        holder.folderName.setText(folderName);
        holder.folderCount.setText(totalMedia + " Items");

        Glide.with(context)
                .load(folderMap.get(folderName).get(0))
                .into(holder.folderImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MediaActivity.class);
            intent.putExtra("folderName", folderName); // Pass folder name
            intent.putStringArrayListExtra("mediaList", folderMap.get(folderName)); // Pass media list
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView folderImage;
        TextView folderName;
        TextView folderCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderImage = itemView.findViewById(R.id.folderImage);
            folderName = itemView.findViewById(R.id.folderName);
            folderCount = itemView.findViewById(R.id.folderCount);
        }
    }
}