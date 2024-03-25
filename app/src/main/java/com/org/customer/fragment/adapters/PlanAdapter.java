package com.org.customer.fragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.clinify.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.org.customer.fragment.interfaces.RecyclerViewClickInterface;
import com.org.customer.fragment.model.Plan;

public class PlanAdapter extends FirebaseRecyclerAdapter<Plan, PlanAdapter.PlanViewHolder> {

    private RecyclerViewClickInterface recycler_view_click_interface;

    public PlanAdapter(@NonNull FirebaseRecyclerOptions<Plan> options,
                       RecyclerViewClickInterface recycler_view_click_interface) {
        super(options);
        this.recycler_view_click_interface = recycler_view_click_interface;
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlanViewHolder holder,
                                    final int position, @NonNull final Plan model) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycler_view_click_interface.onItemClick(position, model);
            }
        });

        holder.days.setText(model.getDays());
        holder.rs.setText(model.getRs());
        holder.desc.setText(model.getDesc());
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_recycler_row, parent, false);
        return new PlanViewHolder(v);
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView days;
        TextView rs;
        TextView desc;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            days = itemView.findViewById(R.id.text_plan);
            rs = itemView.findViewById(R.id.text_rs);
            desc = itemView.findViewById(R.id.text_desc);
        }
    }


}
