package com.example.classattendance;

import static android.content.Intent.getIntent;

import android.content.Context;
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
        int locationState=locationState();
        if(classState==1 && locationState==1) holder.buttonAttendance.setEnabled(true);

        holder.buttonAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this function will not be called if the button is not enabled ,but I've added this check below for a better code flow
                if (holder.buttonAttendance.isEnabled()) {
                    databaseHandler.newAttendance(userId, currentClass.getId());
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
        int userId,classId;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.className);
            title = itemView.findViewById(R.id.classTitle);
            time = itemView.findViewById(R.id.classTime);
            buttonAttendance = itemView.findViewById(R.id.buttonAttendance);
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
        if (currentTime >= start && currentTime <= end) return 1;
        else return 0; // 0 - you are too soon or too late for your attendance to be recorded
    }

    private int locationState() {
        //TODO: implement this function
        int loc = 1;

        if (loc == 1) return 1; //location confirmation for the student
        else return 0; //the student location is not accepted for attendance
    }
}