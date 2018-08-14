package com.bezzo.actors.data.local.sampleDB

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.bezzo.actors.data.local.sampleDB.dao.ActorsDao
import com.bezzo.actors.data.model.Actors
import com.bezzo.actors.di.ApplicationContext
import com.bezzo.actors.util.constanta.AppConstans

/**
 * Created by bezzo on 11/01/18.
 * Add more entities = arrayOf(UserLokal::class, SampleBTable::class)
 * Add more converter must unique
 */
@Database(entities =
    [(Actors::class)], version = 1)
abstract class DycodeDatabase : RoomDatabase() {

    abstract fun actor() : ActorsDao

    companion object {
        @Volatile private var INSTANCE: DycodeDatabase? = null

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn't alter the table, there's nothing else to do here.
            }
        }

        fun getInstance(@ApplicationContext context: Context): DycodeDatabase {
            if (INSTANCE == null) {
                synchronized(DycodeDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context,
                                DycodeDatabase::class.java, AppConstans.DB_NAME)
                                .fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun clearAllTables() {

    }
}