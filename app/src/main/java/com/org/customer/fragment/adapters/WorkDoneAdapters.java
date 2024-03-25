package com.org.customer.fragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.org.clinify.R;
import com.org.customer.fragment.interfaces.WorkDoneViewClickInterface;
import com.org.customer.fragment.model.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WorkDoneAdapters extends FirebaseRecyclerAdapter<Date, WorkDoneAdapters.WorkDoneViewHolder> {


    private final WorkDoneViewClickInterface workDoneViewClickInterface;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public WorkDoneAdapters(@NonNull FirebaseRecyclerOptions<Date> options, WorkDoneViewClickInterface workDoneViewClickInterface) {
        super(options);
        this.workDoneViewClickInterface = workDoneViewClickInterface;

    }

    @Override
    protected void onBindViewHolder(@NonNull WorkDoneViewHolder holder, int position, @NonNull Date model) {
        holder.loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDoneViewClickInterface.onLocClick(position, model);
            }
        });

        holder.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDoneViewClickInterface.onGalleryClick(position, model);
            }
        });

        holder.mesg.setText(getDate(model.getTimeStamp()));

    }

    @NonNull
    @Override
    public WorkDoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work, parent, false);
        return new WorkDoneViewHolder(view);
    }

    private String getDate(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        java.util.Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String t2 = sdf.format(d);
        return t2;
    }



    class WorkDoneViewHolder extends RecyclerView.ViewHolder {
        TextView mesg;
        ImageView loc;
        ImageView gallery;

        public WorkDoneViewHolder(@NonNull View itemView) {
            super(itemView);
            mesg = itemView.findViewById(R.id.msg);
            loc = itemView.findViewById(R.id.location);
            gallery = itemView.findViewById(R.id.gallerys);

        }
    }
}
