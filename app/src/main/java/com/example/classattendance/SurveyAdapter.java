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

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {
    private Context context;
    private List<SurveyModel> surveys;
    public SurveyAdapter(Context context,List<SurveyModel> survey) {
        this.context=context;
        this.surveys = survey;
    }
    @NonNull
    @Override
    public SurveyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_row, parent, false);
        return new SurveyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyAdapter.ViewHolder holder, int position) {
        SurveyModel survey = surveys.get(position);
        holder.areaGrade.setText(survey.getGrade());
        holder.areaComment.setText(survey.getComment());
    }

    @Override
    public int getItemCount() {
        return surveys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView areaGrade,areaComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            areaGrade = itemView.findViewById(R.id.areaGrade);
            areaComment= itemView.findViewById(R.id.areaComment);
        }
    }
}