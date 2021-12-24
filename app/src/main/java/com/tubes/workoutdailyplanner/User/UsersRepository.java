package com.tubes.workoutdailyplanner.User;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.tubes.workoutdailyplanner.AppDatabase;
import com.tubes.workoutdailyplanner.Reminders;
import com.tubes.workoutdailyplanner.RoomDAO;

import java.util.List;

public class UsersRepository {

    private RoomDAO roomDAO;
    private AppDatabase appDatabase;
    private LiveData<List<Reminders>> reminders;

    public UsersRepository(Application aplication) {
        appDatabase = AppDatabase.geAppdatabase(aplication);
        roomDAO = appDatabase.getRoomDAO();
        reminders = roomDAO.getAll();
    }

    public LiveData<List<Reminders>> getAll(){
        return roomDAO.getAll();

    }

    public void Delete(final Reminders reminders){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getRoomDAO().Delete(reminders);
                return null;
            }
        }.execute();
    }

    public void Update(final Reminders reminders){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getRoomDAO().Update(reminders);
                return null;
            }
        }.execute();
    }
}
