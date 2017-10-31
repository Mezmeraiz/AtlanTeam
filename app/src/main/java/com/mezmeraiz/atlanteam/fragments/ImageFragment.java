package com.mezmeraiz.atlanteam.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mezmeraiz.atlanteam.R;
import com.mezmeraiz.atlanteam.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

/**
 * Created by max on 31.10.17.
 */

public class ImageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, null);
        Picasso.with(getContext()).load(getArguments().getString("url"))
                .into((ImageView) v.findViewById(R.id.imageView_photo), new Callback() {
                    @Override
                    public void onSuccess() {
                        v.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Utils.snackBar(getActivity(),"Error");
                        getActivity()
                                .getSupportFragmentManager()
                                .popBackStack();
                    }
                });
        return v;
    }

    public static ImageFragment create(String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }
}
