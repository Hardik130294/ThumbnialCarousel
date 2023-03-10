package com.example.thumbnailcarousel.ui.slideshow;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thumbnailcarousel.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterThumbnail extends RecyclerView.Adapter<AdapterThumbnail.ThumbnailVH> {

    private int selectedPosition = 0;
    private final OnClickCallBack onThumbnailClick;

    ArrayList<ArrayList<String>> tableImage;

    public AdapterThumbnail(@NotNull ArrayList<ArrayList<String>> tableImage, @NotNull OnClickCallBack onThumbnailClick) {
        this.tableImage = tableImage;
        this.onThumbnailClick = onThumbnailClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public final void updateSelectedPosition(int position) {
        this.selectedPosition = position;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThumbnailVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail_item, parent, false);
        return new ThumbnailVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThumbnailVH holder, @SuppressLint("RecyclerView") int position) {
        if (tableImage.get(position).get(0) != null) {

            Glide.with(holder.imageView.getContext())
                    .load(Uri.parse("https://app.jivado.com/" + tableImage.get(position).get(0)))
                    .error(R.drawable.image1)
                    .into(holder.imageView);
            if (this.selectedPosition == position) {
                holder.relativeLayout.setBackgroundResource(R.drawable.selected_border);
            } else {
                holder.relativeLayout.setBackgroundResource(R.drawable.default_border);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onThumbnailClick.OnThumbnailClick(position);
                    Log.d("TAG", "ThumbnailAdapter: " + position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (tableImage != null)
            return tableImage.size();
        else
            return 0;
    }

    public static class ThumbnailVH extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        ImageView imageView;

        public ThumbnailVH(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.rl_thumbnail);
            imageView = itemView.findViewById(R.id.iv_thumbnail);
        }
    }
}
