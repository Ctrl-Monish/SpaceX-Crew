package com.internship.spacexcrew;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CrewMember.class}, version = 1)
public abstract class CrewDatabase extends RoomDatabase {
    private static CrewDatabase instance;
    public abstract CrewDao crewDao();
    public static synchronized CrewDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                     CrewDatabase.class, "crew_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    };
}
