package com.tubes.workoutdailyplanner;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDAO {


    @Insert
    void Insert(Reminders... reminders);

    @Update
    void Update(Reminders... reminders);

    @Delete
    void Delete(Reminders reminders);

    @Query("select message, remindDate from reminder where message = :message LIMIT 1")
    Reminders deleteData(String message);

    @Query("SELECT * FROM reminder order by remindDate")
    List<Reminders> orderThetable();

    @Query("Select * from reminder Limit 1")
    List<Reminders> getRecentEnteredData();

    @Query("Select * from reminder")
    LiveData<List<Reminders>> getAll();

}
