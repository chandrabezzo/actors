package com.bezzo.actors.data.local

import android.content.Context
import com.bezzo.actors.data.local.sampleDB.DycodeDatabase
import com.bezzo.actors.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 11/01/18.
 */

@Singleton
class LocalStorageHelper @Inject
constructor(@ApplicationContext context: Context) {

    // add all Database Local
    val dycodeDatabase : DycodeDatabase = DycodeDatabase.getInstance(context)
}