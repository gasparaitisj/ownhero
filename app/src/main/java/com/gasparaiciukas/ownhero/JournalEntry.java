package com.gasparaiciukas.ownhero;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;
/*
A journal entry contains:
an id, parent step id, text (journal entry content), creation date, creation time
*/
@Entity(tableName = "journal")
public class JournalEntry {

    @PrimaryKey
    public int id;

    @ForeignKey(entity = Step.class, parentColumns="id", childColumns="step_id")
    public int step_id;

    @ColumnInfo (name = "text")
    public String text;

    @ColumnInfo (name = "date")
    public Date date;

    @ColumnInfo(name = "time")
    public Time time;
}
