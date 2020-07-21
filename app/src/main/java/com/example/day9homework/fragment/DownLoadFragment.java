package com.example.day9homework.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.day9homework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownLoadFragment extends Fragment {


    public DownLoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_down_load, container, false);
    }

}
