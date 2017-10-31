package com.mezmeraiz.atlanteam.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mezmeraiz.atlanteam.R;

/**
 * Created by max on 31.10.17.
 */

public class TextFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text, null);
        ((TextView)v.findViewById(R.id.textView_text))
                .setText(getArguments().getString("text"));
        return v;
    }

    public static TextFragment create(String text) {
        TextFragment fragment = new TextFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragment.setArguments(bundle);
        return fragment;
    }

}
