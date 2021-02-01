package com.gasparaiciukas.ownhero;

import androidx.room.Dao;
import androidx.room.Query;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

// SQL code for managing the database
@Dao
public interface GoalDao {

    @Query("INSERT INTO goals (title, completed, date, time) VALUES (:title, 0, :date, :time)")
    void goal_create(String title, Date date, Time time);

    @Query("SELECT * FROM goals ORDER BY date DESC")
    List<Goal> goal_getAll();

    @Query("SELECT * FROM goals WHERE completed = :status ORDER BY date DESC, time DESC")
    List<Goal> goal_getByCompletion(boolean status);

    @Query("DELETE FROM goals WHERE id = :id")
    void goal_delete(int id);

    @Query("UPDATE goals SET title = :title WHERE id = :id")
    void goal_updateTitle (int id, String title);

    @Query("UPDATE goals SET completed = :status WHERE id = :id")
    void goal_updateStatus (int id, boolean status);

    @Query("UPDATE goals SET date = :date WHERE id = :id")
    void goal_updateDate (int id, Date date);

    @Query("UPDATE goals SET time = :time WHERE id = :id")
    void goal_updateTime (int id, Time time);

    @Query("SELECT * FROM goals WHERE id = :goalId")
    Goal goal_getById(int goalId);

    @Query("INSERT INTO steps (title, completed, date, time, goal_id) VALUES (:title, 0, :date, :time, :goalId)")
    void step_create(String title, Date date, Time time, int goalId);

    @Query("SELECT * FROM steps")
    List<Step> step_getAll();

    @Query("SELECT * FROM steps WHERE goal_id = :goalId ORDER BY date DESC, time DESC")
    List<Step> step_getAllByGoalId(int goalId);

    @Query("DELETE FROM steps WHERE id = :id")
    void step_delete(int id);

    @Query("DELETE FROM steps WHERE goal_id = :goalId")
    void step_deleteAllByGoalId(int goalId);

    @Query("UPDATE steps SET title = :title WHERE id = :id")
    void step_updateTitle (int id, String title);

    @Query("UPDATE steps SET completed = :status WHERE id = :id")
    void step_updateStatus (int id, boolean status);

    @Query("UPDATE steps SET date = :date WHERE id = :id")
    void step_updateDate (int id, Date date);

    @Query("UPDATE steps SET time = :time WHERE id = :id")
    void step_updateTime (int id, Time time);

    @Query("SELECT * FROM steps WHERE id = :stepId")
    Step step_getById(int stepId);

    @Query("INSERT INTO journal (step_id, text, date, time) VALUES (:stepId, :text, :date, :time)")
    void journal_create(int stepId, String text, Date date, Time time);

    @Query("SELECT * FROM journal WHERE step_id = :stepId ORDER BY date DESC, time DESC")
    List<JournalEntry> journal_getAllByStepId(int stepId);

    @Query("SELECT * FROM journal WHERE id = :journalId")
    JournalEntry journal_getById(int journalId);

    @Query("DELETE FROM journal WHERE id = :id")
    void journal_delete(int id);

    @Query("DELETE FROM journal WHERE step_id = :stepId")
    void journal_deleteAllByStepId(int stepId);

    @Query("UPDATE journal SET text = :text WHERE id = :id")
    void journal_updateText (int id, String text);

    @Query("UPDATE journal SET date = :date WHERE id = :id")
    void journal_updateDate (int id, Date date);

    @Query("UPDATE journal SET time = :time WHERE id = :id")
    void journal_updateTime (int id, Time time);
}
