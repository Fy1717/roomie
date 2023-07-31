package com.example.roomie.model.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(UserEntity userEntity);

    @Update
    void update(UserEntity userEntity);

    @Query("SELECT * from user_table WHERE uid=1")
    UserEntity getUser();

    @Query("DELETE from user_table")
    void delete();
}
