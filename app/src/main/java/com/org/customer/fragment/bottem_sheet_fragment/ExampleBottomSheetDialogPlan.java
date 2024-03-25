package com.org.customer.fragment.bottem_sheet_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.org.clinify.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExampleBottomSheetDialogPlan extends BottomSheetDialogFragment implements View.OnClickListener {

    private BottomSheetDialogPlan m_listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottem_sheet_layout_plan, container, false);

        //findind views
        Button button1 = v.findViewById(R.id.button1);
        Button button2 = v.findViewById(R.id.button2);

        //setting listeners
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            m_listener.onButtonClickedPlan("Button 1 clicked");
            dismiss();
        }

        if (v.getId() == R.id.button2) {
            dismiss();
        }
    }

    public interface BottomSheetDialogPlan {
        void onButtonClickedPlan(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            m_listener = (BottomSheetDialogPlan) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ExampleBottomSheetDialogPlanPlan");
        }
    }
}