package com.bezzo.actors.features.detail

import android.os.Bundle
import com.bezzo.actors.R
import com.bezzo.actors.base.BaseActivity
import com.bezzo.actors.data.model.Actors
import com.bezzo.actors.util.GlideApp
import com.bezzo.actors.util.constanta.AppConstans
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity() {

    @Inject
    lateinit var gson: Gson

    override fun onInitializedView(savedInstanceState: Bundle?) {
        activityComponent.inject(this)

        displayHome()
        setActionBarTitle(getString(R.string.detail))

        var actor = gson.fromJson<Actors>(dataReceived?.getString(AppConstans.ACTOR), Actors::class.java)

        GlideApp.with(this)
                .load(actor.image)
                .circleCrop()
                .into(iv_image)
        tv_name.text = actor.name
        tv_dob.text = actor.dob
        tv_desc.text = actor.description
    }

    override fun setLayout(): Int {
        return R.layout.activity_detail
    }
}
