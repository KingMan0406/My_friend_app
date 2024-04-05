package com.example.my_friend_app.room;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.my_friend_app.models.Student;
@Database(entities = {Student.class},version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract Student studentDao();


}
