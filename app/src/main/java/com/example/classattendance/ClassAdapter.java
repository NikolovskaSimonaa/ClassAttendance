package com.example.classattendance;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    int userId;
    public ClassAdapter(Context context, List<ClassModel> classList,DatabaseHandler databaseHandler,int userId) {
        this.context = context;
        this.classList = classList;
        this.databaseHandler = databaseHandler;
        this.userId = userId;
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
        String formattedStartDateTime = formatDateAndTime(currentClass.getStartTimestamp());
        holder.time.setText(formattedStartDateTime);

        int classState=classState(currentClass.getStartTimestamp(), currentClass.getEndTimestamp());
        if(classState==1) holder.buttonAttendance.setEnabled(true);
        if(classState==2){ // the current class ended
            if(databaseHandler.checkAttendance(userId,currentClass.getId())) {
                holder.buttonSurvey.setEnabled(true);
            }
        }
        holder.buttonAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this function will not be called if the button is not enabled ,but I've added this check below for a better code flow
                if (holder.buttonAttendance.isEnabled()) {
                    databaseHandler.newAttendance(userId, currentClass.getId());
                    Toast.makeText(context, "Attendance recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.buttonSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this function will not be called if the button is not enabled ,but I've added this check below for a better code flow
                if (holder.buttonSurvey.isEnabled()) {
                    Context context=v.getContext();
                    Intent intent=new Intent(context, SurveyActivity.class);
                    intent.putExtra("USER_ID", userId);
                    intent.putExtra("CLASS_ID", currentClass.getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        TextView title;
        TextView time;
        Button buttonAttendance;
        Button buttonSurvey;
        int userId,classId;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.className);
            title = itemView.findViewById(R.id.classTitle);
            time = itemView.findViewById(R.id.classTime);
            buttonAttendance = itemView.findViewById(R.id.buttonAttendance);
            buttonSurvey = itemView.findViewById(R.id.buttonSurvey);
        }
    }
    private String formatDateAndTime(String timestamp) {
        long timestampMillis=Long.parseLong(timestamp);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date=new Date(timestampMillis);
        return sdf.format(date);
    }

    private int classState(String startTime, String endTime){
        long currentTime=System.currentTimeMillis();
        long start = Long.parseLong(startTime);
        long end = Long.parseLong(endTime);
        if (currentTime >= start && currentTime <= end) return 1; //1 - you are on time
        else if (currentTime < start) return 0; // 0 - you are too soon for your attendance to be recorded
        else return 2; // 2 - you are too late for your attendance to be recorded, or if the student attended the class,
                       // the button for filling the survey form will be enabled.
    }
}