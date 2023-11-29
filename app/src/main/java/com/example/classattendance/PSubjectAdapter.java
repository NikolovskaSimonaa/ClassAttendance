package com.example.classattendance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PSubjectAdapter extends RecyclerView.Adapter<PSubjectAdapter.ViewHolder> {

    private List<SubjectModel> subjects;
    private NewClassClickListener NewClassClickListener;
    private int userId;

    public interface NewClassClickListener { //interface for the click listener
        void onNewClassClick(int subjectId);
    }
    public void setNewClassClickListener(PSubjectAdapter.NewClassClickListener listener) {
        this.NewClassClickListener = listener;
    }
    public PSubjectAdapter(List<SubjectModel> subjects, int userId) {
        this.userId = userId;
        this.subjects = subjects;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.psubject_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubjectModel subject = subjects.get(position);
        holder.subName.setText(subject.getName());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, NewClassActivity.class);
                intent.putExtra("SUBJECT_ID", subject.getId());
                intent.putExtra("USER_ID", userId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subName;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subName = itemView.findViewById(R.id.subName);
            button = itemView.findViewById(R.id.buttonNewClass);
        }
    }
}