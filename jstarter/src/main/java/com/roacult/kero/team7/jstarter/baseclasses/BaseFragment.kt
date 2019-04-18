package com.roacult.kero.team7.jstarter.baseclasses

import android.content.Context
import dagger.android.support.DaggerFragment

open class BaseFragment :DaggerFragment(){

    private var mActivity: BaseActivity? = null

    fun hideKeyboeard() {
        mActivity?.hideKeyboard()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    fun showToast(message: String) {
        mActivity?.showToast(message)
    }

    fun onError(resId: Int) {
        mActivity?.onError(resId)
    }

    fun onError(message: String) {
        mActivity?.onError(message)
    }

    fun showMessage(message: String) {
        mActivity?.showMessage(message)
    }

    fun showMessage(resId: Int) {
        mActivity?.showMessage(resId)
    }

    fun isNetworkConnected(): Boolean {
        return mActivity?.isNetworkConnected() ?: false
    }
}