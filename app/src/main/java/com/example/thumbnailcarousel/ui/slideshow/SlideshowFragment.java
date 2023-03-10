package com.example.thumbnailcarousel.ui.slideshow;

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
import com.example.thumbnailcarousel.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    @SuppressLint("StaticFieldLeak")
    static View root;
    static ArrayList<ArrayList<String>> tableImage = new ArrayList<>();

    AdapterImage adapterImage;
    AdapterThumbnail adapterThumbnail;

    static LinearLayout mDotLayout;
    static TextView[] dots;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        textView.setVisibility(View.GONE);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        //2d ArrayList
        ArrayList<String> blueColor = new ArrayList<>();
        blueColor.add("2022/12/30/catid-1673-jpg_06-19-42.png");
        blueColor.add("2022/12/30/brass-components-1674-jpg_06-20-25.jpg");
        blueColor.add("2022/12/30/brass-nuts-bolts-1697-jpg_06-21-02.jpg");
        blueColor.add("2022/12/30/brass-inserts-1696-jpg_06-21-40.jpg");
        ArrayList<String> redColor = new ArrayList<>();
        redColor.add("2022/12/30/brass-fittings-1677-jpg_06-23-00.jpg");
        redColor.add("2022/12/30/brass-hinges-1692-jpg_06-23-48.jpg");
        redColor.add("2022/12/30/brass-valves-1689-jpg_06-24-24.jpg");
        redColor.add("2022/12/30/brass-cable-gland-1679-jpg_06-25-03.jpg");
        redColor.add("2022/12/30/brass-extruded-rod-1678-jpg_06-31-01.jpg");
        ArrayList<String> pinkColor = new ArrayList<>();
        pinkColor.add("2022/12/30/brass-fasteners-1676-jpg_06-36-51.jpg");
        pinkColor.add("2022/12/30/brass-terminals-1695-jpg_06-37-35.jpg");
        pinkColor.add("2022/12/30/brass-builders-hardware-1680-jpg_06-38-23.jpg");
        ArrayList<String> orangeColor = new ArrayList<>();
        orangeColor.add("2022/12/30/brass-scrap-1686-jpg_06-39-16.jpg");
        orangeColor.add("2022/12/30/brass-anchors-1691-jpg_06-39-47.jpg");
        ArrayList<String> yellowColor = new ArrayList<>();
        yellowColor.add("2022/12/30/brass-rivets-1693-jpg_06-40-20.jpg");
        yellowColor.add("2022/12/30/brass-turned-components-1688-jpg_06-40-56.jpg");
        yellowColor.add("2022/12/30/brass-furniture-fitting-parts-1682-jpg_06-41-30.jpg");
        yellowColor.add("2022/12/30/brass-auto-parts-1675--1--jpg_06-42-04.jpg");
        yellowColor.add("2022/12/30/brass-pneumatic-fittings-1685-jpg_06-42-48.jpg");
        yellowColor.add("2022/12/30/brass-forged-components-1681-jpg_06-43-23.jpg");
        ArrayList<String> greenColor = new ArrayList<>();
        greenColor.add("2022/12/30/brass-stove-parts-1687-jpg_06-44-12.jpg");
        greenColor.add("2022/12/30/brass-transformer-parts-1690-jpg_06-44-48.jpg");
        ArrayList<String> brownColor = new ArrayList<>();
        brownColor.add("2022/12/30/brass-turning-parts-1683-jpg_06-45-22.jpg");
        brownColor.add("2022/12/30/catid-180-jpg_06-52-53.jpg");
        brownColor.add("2022/12/30/chemical-supplies-830-jpg_06-53-36.jpg");

        tableImage.add(blueColor);
        tableImage.add(redColor);
        tableImage.add(pinkColor);
        tableImage.add(orangeColor);
        tableImage.add(yellowColor);
        tableImage.add(greenColor);
        tableImage.add(brownColor);

        adapterImage = new AdapterImage(tableImage, 0);//default load
        binding.viewPager2BigImage.setAdapter(adapterImage);

        adapterThumbnail = new AdapterThumbnail(tableImage, new OnClickCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void OnThumbnailClick(int position) {
                adapterThumbnail.updateSelectedPosition(position);
                binding.recyclerViewSmallImage.smoothScrollToPosition(position);

                adapterImage = new AdapterImage(tableImage, position);
                binding.viewPager2BigImage.setAdapter(adapterImage);

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
            }
        });

        return root;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setUpIndicator(int position) {
        mDotLayout = (LinearLayout) root.findViewById(R.id.indicator_layout);
        mDotLayout.removeAllViews();
//        dots = new TextView[number[ImageAdapter.position].length];
        dots = new TextView[tableImage.get(AdapterImage.position).size()];

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