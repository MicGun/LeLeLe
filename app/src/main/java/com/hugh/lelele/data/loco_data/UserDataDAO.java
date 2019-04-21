package com.hugh.lelele.data.loco_data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDataDAO {

    @Insert
    public void insert(UserData... items);

    @Update
    public void update(UserData... items);

    @Delete
    public void delete(UserData item);

    @Query("SELECT * FROM userData")
    public List<UserData> getItems();

    @Query("SELECT * FROM userData WHERE mId LIKE :id")
    public UserData getUserById(String id);

    @Query("DELETE FROM userData")
    public void nukeTable();
}
