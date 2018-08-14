package com.bezzo.actors.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.bezzo.actors.util.constanta.AppConstans
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = AppConstans.ACTOR)
class Actors {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id : Int? = null

    @SerializedName("name")
    @ColumnInfo(name = "name")
    @Expose
    var name: String? = null

    @SerializedName("description")
    @ColumnInfo(name = "description")
    @Expose
    var description: String? = null

    @SerializedName("dob")
    @ColumnInfo(name = "dob")
    @Expose
    var dob: String? = null

    @SerializedName("country")
    @ColumnInfo(name = "country")
    @Expose
    var country: String? = null

    @SerializedName("height")
    @ColumnInfo(name = "height")
    @Expose
    var height: String? = null

    @SerializedName("spouse")
    @ColumnInfo(name = "spouse")
    @Expose
    var spouse: String? = null

    @SerializedName("children")
    @ColumnInfo(name = "children")
    @Expose
    var children: String? = null

    @SerializedName("image")
    @ColumnInfo(name = "image")
    @Expose
    var image: String? = null
}