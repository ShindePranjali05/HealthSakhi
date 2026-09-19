package com.example.healthsakhi.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();  // Executor to handle database operations on a background thread

    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "user_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();  // Removed allowMainThreadQueries() to prevent blocking UI thread
        }
        return instance;
    }

    // Utility method to execute tasks on a background thread
    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
