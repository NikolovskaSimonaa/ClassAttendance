package com.example.classattendance;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ESubjectAdapter extends RecyclerView.Adapter<ESubjectAdapter.ViewHolder> {

    private List<SubjectModel> subjects;
    private EnrollClickListener enrollClickListener;
    private int userId;
    public ESubjectAdapter(List<SubjectModel> subjects, int userId) {
        this.subjects = subjects;
        this.userId = userId;
    }
    public void setSubjects(List<SubjectModel> updatedSubjects) {
        this.subjects = updatedSubjects;
    }
    public interface EnrollClickListener { //interface for the click listener
        void onEnrollClick(int subjectId, int userId);
    }
    public void setEnrollClickListener(EnrollClickListener listener) {
        this.enrollClickListener = listener;
    }
    @NonNull
    @Override
    public ESubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enroll_row, parent, false);
        return new ESubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ESubjectAdapter.ViewHolder holder, int position) {
        SubjectModel subject = subjects.get(position);
        holder.eSubName.setText(subject.getName());

        holder.buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enrollClickListener != null) {
                    enrollClickListener.onEnrollClick(subject.getId(), userId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eSubName;
        Button buttonE;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eSubName = itemView.findViewById(R.id.eSubName);
            buttonE = itemView.findViewById(R.id.buttonEnroll);
        }
    }
}