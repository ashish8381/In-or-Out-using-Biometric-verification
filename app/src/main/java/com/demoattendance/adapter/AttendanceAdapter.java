package com.demoattendance.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demoattendance.R;
import com.demoattendance.modal.attendanceModel;
import com.demoattendance.modal.attendanceModel2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private ArrayList<attendanceModel2> attendanceList;
    private Context context;

    public AttendanceAdapter(ArrayList<attendanceModel2> attendanceList,Context context) {
        this.attendanceList = attendanceList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        attendanceModel2 model = attendanceList.get(position);


//        if (position % 2 == 0 && position + 1 < getItemCount()) {

            holder.mmain.setVisibility(View.VISIBLE);
            Date parsedDate;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                parsedDate = sdf.parse(model.getIn_timestamp());
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }

            // Format the date and time separately

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            String formattedDate = dateFormat.format(parsedDate);
            String formattedTime = timeFormat.format(parsedDate);
            holder.mdatetoday.setText(getDayLabel(model.getIn_timestamp()));
            holder.minTimeTextView.setText("IN TIME  " + formattedTime);
        if(!model.getOut_timestamp().equals("na")) {
            // Show the out-time for the same row
            Date parsedDate2;
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                parsedDate2 = sdf2.parse(model.getOut_timestamp());
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            String formattedDate2 = dateFormat2.format(parsedDate2);
            String formattedTime2 = timeFormat2.format(parsedDate2);

            holder.mdate.setText(formattedDate2);
            holder.moutTimeTextView.setText("OUT TIME " + formattedTime2);
        }else{
            holder.mdate.setVisibility(View.GONE);
            holder.moutTimeTextView.setVisibility(View.GONE);
        }
//        }


    }

    public static String getDayLabel(String storedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date;
        try {
            date = sdf.parse(storedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        Calendar storedCalendar = Calendar.getInstance();
        storedCalendar.setTime(date);
        int storedYear = storedCalendar.get(Calendar.YEAR);
        int storedDayOfYear = storedCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentDayOfYear = currentCalendar.get(Calendar.DAY_OF_YEAR);

        if (storedYear == currentYear && storedDayOfYear == currentDayOfYear) {
            return "Today";
        } else if (currentYear == storedYear && currentDayOfYear - storedDayOfYear == 1) {
            return "Yesterday";
        } else {
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return outputDateFormat.format(date);
        }
    }


    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mdatetoday, minTimeTextView, moutTimeTextView, mdate;
        RelativeLayout mmain;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mdatetoday = itemView.findViewById(R.id.datetoday);
            minTimeTextView = itemView.findViewById(R.id.intime);
            moutTimeTextView = itemView.findViewById(R.id.outtime);
            mdate = itemView.findViewById(R.id.date);

            mmain=itemView.findViewById(R.id.mainlayout);
        }
    }
}
