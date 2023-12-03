package com.example.classattendance;

import android.content.Context;
import android.content.Intent;
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

public class ClassesForSubjectAdapter extends RecyclerView.Adapter<ClassesForSubjectAdapter.ViewHolder> {
    private Context context;
    private List<ClassModel> classList;
    DatabaseHandler databaseHandler;
    int userId,subjectId;
    public ClassesForSubjectAdapter(Context context,List<ClassModel> classList,DatabaseHandler databaseHandler,int userId, int subjectId) {
        this.classList = classList;
        this.context=context;
        this.databaseHandler=databaseHandler;
        this.userId=userId;
        this.subjectId=subjectId;
    }
    @NonNull
    @Override
    public ClassesForSubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_for_subject_row, parent, false);
        return new ClassesForSubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassesForSubjectAdapter.ViewHolder holder, int position) {
        ClassModel c = classList.get(position);
        holder.subject.setText(databaseHandler.getSubjectForClass(c.getId()));
        holder.title.setText(c.getTitle());
        String formattedStartDateTime = formatDateAndTime(c.getStartTimestamp());
        holder.time.setText(formattedStartDateTime);
        if(Long.parseLong(c.getStartTimestamp()) > System.currentTimeMillis()){
            holder.buttonAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "The class isn't started yet", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            holder.buttonAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context=v.getContext();
                    Intent intent=new Intent(context, AttendedStudentsForClassActivity.class);
                    intent.putExtra("USER_ID", userId);
                    intent.putExtra("SUBJECT_ID", subjectId);
                    intent.putExtra("CLASS_ID", c.getId());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    private String formatDateAndTime(String timestamp) {
        long timestampMillis=Long.parseLong(timestamp);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date=new Date(timestampMillis);
        return sdf.format(date);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        TextView title;
        TextView time;
        Button buttonAttendance;
        int userId,classId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.className);
            title = itemView.findViewById(R.id.classTitle);
            time = itemView.findViewById(R.id.classTime);
            buttonAttendance = itemView.findViewById(R.id.attendance);
        }
    }
}
