package com.example.leachpeach.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leachpeach.model.Completion;

import java.util.Date;
import java.util.List;

@Dao
public interface CompletionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Completion completion);

    @Update
    void update(Completion completion);

    @Query("SELECT * FROM completion_table WHERE date = :date")
    LiveData<Completion> getCompletion(Date date);

    @Query("SELECT * FROM completion_table ORDER BY date DESC")
    LiveData<List<Completion>> getAllCompletions();
}
