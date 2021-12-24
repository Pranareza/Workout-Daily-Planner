package com.tubes.workoutdailyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tubes.workoutdailyplanner.Adapter.AdapterReminders;
import com.tubes.workoutdailyplanner.Model.ViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SetReminder extends AppCompatActivity{

    ViewModel userViewModel;
    FloatingActionButton add;
    private Dialog dialog;
    private AppDatabase appDatabase;
    RecyclerView recyclerView;
    AdapterReminders adapter;
    List<Reminders> temp;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        userViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        adapter = new AdapterReminders(temp);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userViewModel.getAll().observe(this, new Observer<List<Reminders>>() {
            @Override
            public void onChanged(List<Reminders> reminders) {

                if (reminders.size() > 0) {
                    adapter.setData(reminders);
                    recyclerView.setAdapter(adapter);
                }

            }
        });

        appDatabase = AppDatabase.geAppdatabase(SetReminder.this);

        add = findViewById(R.id.floatingButton);
        empty = findViewById(R.id.empty);

        add.setOnClickListener(v -> addReminder());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SetReminder.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setItemsInRecyclerView();
    }

    private void addReminder() {
        dialog = new Dialog(SetReminder.this);
        dialog.setContentView(R.layout.floating_popup);

        final TextView textView = dialog.findViewById(R.id.date);
        Button select,add;
        select = dialog.findViewById(R.id.selectDate);
        add = dialog.findViewById(R.id.addButton);
        final EditText message = dialog.findViewById(R.id.message);


        final Calendar newCalender = Calendar.getInstance();
        select.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(SetReminder.this, (view, year, month, dayOfMonth) -> {

                Calendar newTime = Calendar.getInstance();
                TimePickerDialog time = new TimePickerDialog(SetReminder.this, (view1, hourOfDay, minute) -> {

                    newCalender.set(year,month,dayOfMonth,hourOfDay,minute,0);
                    Calendar tem = Calendar.getInstance();
                    Log.w("TIME",System.currentTimeMillis()+"");
                    if(newCalender.getTimeInMillis()-tem.getTimeInMillis()>0)
                        textView.setText(newCalender.getTime().toString());
                    else
                        Toast.makeText(SetReminder.this,"Invalid time",Toast.LENGTH_SHORT).show();

                },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),true);
                time.show();

            },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));

            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();

        });


        add.setOnClickListener(v -> {

            RoomDAO roomDAO = appDatabase.getRoomDAO();
            Reminders reminders = new Reminders();
            reminders.setMessage(message.getText().toString().trim());
            Date remind = new Date(textView.getText().toString().trim());
            reminders.setRemindDate(remind);
            roomDAO.Insert(reminders);
            LiveData<List<Reminders>> l = roomDAO.getAll();
            Log.e("ID Workout Daily Planner",reminders.getId()+"");

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            calendar.setTime(remind);
            calendar.set(Calendar.SECOND,0);
            Intent intent = new Intent(SetReminder.this, NotifierAlarm.class);
            intent.putExtra("Message",reminders.getMessage());
            intent.putExtra("RemindDate",reminders.getRemindDate().toString());
            intent.putExtra("id",reminders.getId());
            PendingIntent intent1 = PendingIntent.getBroadcast(SetReminder.this,reminders.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);

            Toast.makeText(SetReminder.this,"Berhasil Menambahkan",Toast.LENGTH_SHORT).show();
            setItemsInRecyclerView();
            AppDatabase.destroyInstance();
            dialog.dismiss();


        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void setItemsInRecyclerView() {
        RoomDAO dao = appDatabase.getRoomDAO();
        temp = dao.orderThetable();
        if(temp.size()>0) {
            empty.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapter = new AdapterReminders(temp);
        recyclerView.setAdapter(adapter);

    }
}
