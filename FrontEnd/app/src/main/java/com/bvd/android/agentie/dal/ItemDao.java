package com.bvd.android.agentie.dal;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bvd.android.agentie.model.Item;

import java.util.List;

/**
 * Created by bara on 2/2/2018.
 */

@Dao
public interface ItemDao {
    @Query("SELECT * FROM  items ")
    List<Item> getAll();

    @Insert
    void insertAll(Item... obj);

    @Update
    void updateCars(Item... obj);

    @Delete
    void delete(Item obj);

}
