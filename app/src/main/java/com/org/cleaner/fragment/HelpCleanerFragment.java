package com.org.cleaner.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.org.clinify.R;


public class HelpCleanerFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_help_cleaner, container, false);
        String str = "Help Fragment For Cleaner ";

        TextView tv =v.findViewById(R.id.helptext);
        tv.setText(str);
        return v;

    }
}