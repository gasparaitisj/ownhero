package com.gasparaiciukas.ownhero;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

// Instantiate Room database
@Database(entities = {Goal.class, Step.class, JournalEntry.class}, version = 1)
@TypeConverters(value = {DateConverter.class})
public abstract class GoalsDatabase extends RoomDatabase {
    public abstract GoalDao goalDao();
}
