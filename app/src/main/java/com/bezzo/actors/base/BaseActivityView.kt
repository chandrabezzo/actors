package com.bezzo.actors.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes

/**
 * Created by bezzo on 21/12/17.
 */

interface BaseActivityView : BaseView {

    fun getContext(): Context?

    fun displayHome()

    fun setActionBarTitle(title: String)

    fun showSnackBar(message: String, duration: Int)

    fun showSnackBar(@StringRes resId : Int, duration: Int)

    fun gotoDialog(dialogClass: Class<*>, data: Bundle?)

    fun onClickBack()
}