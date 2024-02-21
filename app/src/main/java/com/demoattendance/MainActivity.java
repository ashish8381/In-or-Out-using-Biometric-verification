package com.demoattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.demoattendance.adapter.AttendanceAdapter;
import com.demoattendance.db.dbhandler;
import com.demoattendance.modal.attendanceModel;
import com.demoattendance.modal.attendanceModel2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView mimage;
    private dbhandler dbHandler;
    private RecyclerView recyclerView;

//    private RecyclerView.Adapter rmhomeAdapter;
    private ArrayList<attendanceModel> attendanceList;

    private ArrayList<attendanceModel2> attendanceList2;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mimage=findViewById(R.id.scan_finger);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        attendanceList = new ArrayList<>();
        attendanceList2 = new ArrayList<>();
        dbHandler = new dbhandler(MainActivity.this);

        mimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBiometricPrompt();

            }
        });

    }

    private void showBiometricPrompt() {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setDescription("Please authenticate with your biometrics to continue")
                .setDeviceCredentialAllowed(true)
                .build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(this,
                ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);

                        Calendar calendar = Calendar.getInstance();
                        Date date = calendar.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String currentDateAndTime = sdf.format(date);


                        if(!dbHandler.isTableEmpty()){
                            dbHandler.addNewActivity(currentDateAndTime);
                            updatedata();
                        }else{
                            dbHandler.addNewActivity(currentDateAndTime);
                            updatedata();
                        }
                        Toast.makeText(getApplicationContext(), "Authentication successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });

        biometricPrompt.authenticate(promptInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!dbHandler.isTableEmpty()) {
            updatedata();
        }
    }

    private void updatedata(){
        boolean chk=dbHandler.isTableEmpty();
        if(!chk){

            attendanceList = dbHandler.readCourses();
            attendanceList2.clear();
            for (int i = 0; i < attendanceList.size(); i += 2) {
                attendanceModel inTimeModel = attendanceList.get(i);
                attendanceModel outTimeModel = (i + 1 < attendanceList.size()) ? attendanceList.get(i + 1) : null;

                if (outTimeModel != null) {
                    // Create a new attendanceModel2 object with in-time and out-time
                    attendanceModel2 mergedModel = new attendanceModel2(inTimeModel.getId(), inTimeModel.getIn_timestamp(), outTimeModel.getIn_timestamp());
                    attendanceList2.add(mergedModel);
                }else{
                    attendanceModel2 mergedModel = new attendanceModel2(inTimeModel.getId(), inTimeModel.getIn_timestamp(), "na");
                    attendanceList2.add(mergedModel);
                }
            }

            adapter = new AttendanceAdapter(attendanceList2,MainActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}