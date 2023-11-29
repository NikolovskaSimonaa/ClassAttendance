package com.example.classattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private Context context;
    DatabaseHandler databaseHandler;
    private List<ClassModel> classList;
    public ClassAdapter(Context context, List<ClassModel> classList,DatabaseHandler databaseHandler) {
        this.context = context;
        this.classList = classList;
        this.databaseHandler = databaseHandler;
    }
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_row, parent, false);
        return new ClassViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassModel currentClass = classList.get(position);

        holder.subject.setText(databaseHandler.getSubjectForClass(currentClass.getId()));
        holder.title.setText(currentClass.getTitle());
        String formattedDateTime = formatDateAndTime(currentClass.getTimestamp());
        holder.time.setText(formattedDateTime);
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        TextView title;
        TextView time;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.className);
            title = itemView.findViewById(R.id.classTitle);
            time = itemView.findViewById(R.id.classTime);
        }
    }
    private String formatDateAndTime(String timestamp) {
        long timestampMillis=Long.parseLong(timestamp);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date=new Date(timestampMillis);
        return sdf.format(date);
    }
}