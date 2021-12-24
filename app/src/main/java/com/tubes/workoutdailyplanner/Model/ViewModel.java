package com.tubes.workoutdailyplanner.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tubes.workoutdailyplanner.Reminders;
import com.tubes.workoutdailyplanner.User.UsersRepository;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    UsersRepository usersRepository;
    LiveData<List<Reminders>> reminders;

    public ViewModel(@NonNull Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
        reminders = usersRepository.getAll();
    }

    public LiveData<List<Reminders>> getAll(){
        return usersRepository.getAll();
    }

    public void Update(Reminders reminders){
        usersRepository.Update(reminders);
    }

    public void Delete(Reminders reminders){
        usersRepository.Delete(reminders);
    }
}
