package com.gasparaiciukas.ownhero;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;
/*
A step contains:
an id, parent goal id, a title, completion status, creation date, creation time
*/
@Entity(tableName = "steps")
public class Step {

    @PrimaryKey
    public int id;

    @ForeignKey(entity = Goal.class, parentColumns="id", childColumns="goal_id")
    public int goal_id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "completed")
    public boolean completed;

    @ColumnInfo (name = "date")
    public Date date;

    @ColumnInfo(name = "time")
    public Time time;
}