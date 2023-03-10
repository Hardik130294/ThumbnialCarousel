package com.example.thumbnailcarousel.ui.gallery;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thumbnailcarousel.R;
import com.example.thumbnailcarousel.databinding.FragmentGalleryBinding;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    @SuppressLint("StaticFieldLeak")
    static View root;

    static ArrayList<String> imageString;
    AdapterImage adapterImage;
    AdapterThumbnail adapterThumbnail;

    static LinearLayout mDotLayout;
    static TextView[] dots;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        final TextView textView = binding.textGallery;
        textView.setVisibility(View.GONE);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        imageString = new ArrayList<>();
        imageString.add("2022/12/30/brass-fittings-1677-jpg_06-23-00.jpg");
        imageString.add("2022/12/30/brass-hinges-1692-jpg_06-23-48.jpg");
        imageString.add("2022/12/30/brass-valves-1689-jpg_06-24-24.jpg");
        imageString.add("2022/12/30/brass-cable-gland-1679-jpg_06-25-03.jpg");
        imageString.add("2022/12/30/brass-extruded-rod-1678-jpg_06-31-01.jpg");


        adapterImage = new AdapterImage(imageString);//default load
        binding.viewPager2BigImage.setAdapter(adapterImage);

        adapterThumbnail = new AdapterThumbnail(imageString, new OnClickCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void OnThumbnailClick(String sPosition) {
                binding.viewPager2BigImage.setCurrentItem(imageString.indexOf(sPosition));
            }
        });
        binding.recyclerViewSmallImage.setAdapter(adapterThumbnail);
        binding.recyclerViewSmallImage.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        binding.viewPager2BigImage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setUpIndicator(position);
                if (position == 0) {
                    binding.ivRightToLeft.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right_to_left));
                } else {
                    binding.ivRightToLeft.setVisibility(View.GONE);
                    binding.ivRightToLeft.clearAnimation();
                }
                adapterThumbnail.updateSelectedPosition(position);
                binding.recyclerViewSmallImage.smoothScrollToPosition(position);
            }
        });

        return root;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setUpIndicator(int position) {
        mDotLayout = (LinearLayout) root.findViewById(R.id.indicator_layout);
        mDotLayout.removeAllViews();
        dots = new TextView[imageString.size()];

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(root.getContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(root.getResources().getColor(R.color.purple_200, root.getContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(root.getResources().getColor(R.color.purple_700, root.getContext().getTheme()));

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}