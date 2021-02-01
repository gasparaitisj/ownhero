package com.gasparaiciukas.ownhero;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;

/*
A goal contains:
an id, a title, completion status, creation date, creation time
*/

@Entity(tableName = "goals")
public class Goal {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "completed")
    public boolean completed;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "time")
    public Time time;
}
