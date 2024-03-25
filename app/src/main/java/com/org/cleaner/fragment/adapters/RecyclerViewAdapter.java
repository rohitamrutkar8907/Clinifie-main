package com.org.cleaner.fragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.cleaner.fragment.interfaces.RecyclerViewClickInterface;
import com.org.cleaner.fragment.model.Customer;
import com.org.clinify.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Customer> moviesList;
    RecyclerViewClickInterface recycler_viewclickinterface;

    public RecyclerViewAdapter(List<Customer> moviesList,RecyclerViewClickInterface recycler_viewclickinterface) {
        this.moviesList = moviesList;
        this.recycler_viewclickinterface = recycler_viewclickinterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(moviesList.get(position).getName());
        holder.ratingTextView.setText(moviesList.get(position).getCar_number());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView ratingTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycler_viewclickinterface.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
