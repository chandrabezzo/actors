package com.bezzo.actors.data.local.sampleDB.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.bezzo.actors.data.model.Actors
import com.bezzo.actors.util.constanta.AppConstans
import io.reactivex.Flowable


/**
 * Created by bezzo on 11/01/18.
 * if you want to check the value is null or not use "attributeName is null"
 * Room can't check boolean, boolean type will convert to numeric
 * if your column name isAttribute, will detected automatic same as boolean value
 * if your column value is object or array you must add converter
 */

@Dao
interface ActorsDao {

    @Query("SELECT * FROM " + AppConstans.ACTOR)
    fun getAll(): Flowable<List<Actors>>

    @Query("SELECT * FROM " + AppConstans.ACTOR
            + " LIMIT 1")
    fun get(): Flowable<Actors>

    @Query("SELECT * FROM " + AppConstans.ACTOR
            + " LIMIT :limit")
    fun getLimit(limit : Int): Flowable<Actors>

    @Query("SELECT * FROM " + AppConstans.ACTOR
            + " WHERE id=:id")
    fun get(id: Int): Flowable<Actors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value : Actors)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(values : ArrayList<Actors>)

    @Query("DELETE FROM " + AppConstans.ACTOR)
    fun deleteAll()

    @Query("DELETE FROM " + AppConstans.ACTOR
            + " WHERE id=:id")
    fun delete(id: Int)

    @Query("SELECT COUNT(*) FROM " + AppConstans.ACTOR)
    fun count(): Int
}