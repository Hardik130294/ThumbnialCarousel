package com.example.thumbnailcarousel.ui.gallery;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thumbnailcarousel.R;

import java.util.ArrayList;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ImageVH> {
    public static int position;
    public static ArrayList<String> tableImage;

    public AdapterImage(ArrayList<String> tableImage) {
        AdapterImage.tableImage = tableImage;
    }

    @SuppressLint("NotifyDataSetChanged")
    public final void updateSelectedPosition(ArrayList<String> tableImage) {
        AdapterImage.tableImage = tableImage;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageVH(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ImageVH holder, int position) {
        if (tableImage.get(position) != null) {

            Glide.with(holder.imageView.getContext())
                    .load(Uri.parse("https://app.jivado.com/" + tableImage.get(position)))
                    .error(R.drawable.image1)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (tableImage != null)
            return tableImage.size();
        else
            return 0;
    }


    public static class ImageVH extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
        }
    }
}
