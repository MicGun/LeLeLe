package com.hugh.lelele.data.loco_data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {UserData.class}, version = 2)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDataDAO getUserDAO();
}
