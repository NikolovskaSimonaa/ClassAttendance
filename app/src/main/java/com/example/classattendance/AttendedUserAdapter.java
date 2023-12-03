package com.example.classattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendedUserAdapter extends RecyclerView.Adapter<AttendedUserAdapter.ViewHolder> {
    private Context context;
    private List<UserModel> students;

    public AttendedUserAdapter(Context context,List<UserModel> students) {
        this.students = students;
        this.context=context;
    }
    @NonNull
    @Override
    public AttendedUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ssubject_row, parent, false);
        return new AttendedUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendedUserAdapter.ViewHolder holder, int position) {
        UserModel student = students.get(position);
        holder.name.setText(student.getName() + " " + student.getSurname());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.subName);
        }
    }
}
