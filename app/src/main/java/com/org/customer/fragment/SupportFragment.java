package com.org.customer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.org.clinify.R;


public class SupportFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_support, container, false);
        String str = "HELP AND SUPPORT\n" +
                "\n" +
                "\n" +
                " Feel free to contact us at : " + "\n+91 1234567890";
        TextView tv = v.findViewById(R.id.txt);
        tv.setText(str);
        return v;
    }
}