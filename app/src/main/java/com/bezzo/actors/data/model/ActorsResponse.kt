package com.bezzo.actors.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ActorsResponse {
    @SerializedName("actors")
    @Expose
    var actors: List<Actors>? = null
}