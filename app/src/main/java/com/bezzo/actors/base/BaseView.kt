package com.bezzo.actors.base

import android.os.Bundle
import android.support.annotation.StringRes

/**
 * Created by bezzo on 21/12/17.
 */
interface BaseView {

    fun isNetworkConnected() : Boolean

    fun showLoading()

    fun hideLoading()

    fun openActivityOnTokenExpire()

    fun hideKeyboard()

    fun showToast(message: String, duration: Int)

    fun showToast(@StringRes resId: Int, duration: Int)

    fun goToActivity(c: Class<*>, bundle: Bundle?, isFinish: Boolean)

    fun goToActivityClearAllStack(c: Class<*>, bundle: Bundle?)

    fun goToActivityForResult(c: Class<*>, bundle: Bundle?, result: Int)

    fun finishActivityforResult(bundle: Bundle?, result: Int)

    fun gotoFragment(contentReplace: Int, data: Bundle?, classFragment: Class<*>)

    fun showProgressDialog(message: String, cancelable: Boolean)

    fun dismissProgressDialog()

    fun handleError(case : Int)
}