package com.internship.spacexcrew;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CrewDao {
    @Insert
    void insert(CrewMember... crewMember);

    @Query("DELETE FROM CrewMember")
    void deleteAll();

    //show this in recycler view
    @Query("SELECT * FROM CrewMember ORDER BY name ASC")
    List<CrewMember> getAllCrew();
}
