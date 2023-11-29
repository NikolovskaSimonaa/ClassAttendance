package com.example.classattendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SSubjectAdapter extends RecyclerView.Adapter<SSubjectAdapter.ViewHolder> {

    private List<SubjectModel> subjects;
    public SSubjectAdapter(List<SubjectModel> subjects) {
        this.subjects = subjects;
    }
    @NonNull
    @Override
    public SSubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ssubject_row, parent, false);
        return new SSubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SSubjectAdapter.ViewHolder holder, int position) {
        SubjectModel subject = subjects.get(position);
        holder.subName.setText(subject.getName());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subName = itemView.findViewById(R.id.subName);
        }
    }
}
