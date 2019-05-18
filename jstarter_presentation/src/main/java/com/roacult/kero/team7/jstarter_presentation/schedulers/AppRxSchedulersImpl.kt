package com.roacult.kero.team7.jstarter_presentation.schedulers

import com.roacult.kero.team7.jstarter_domain.functional.AppRxSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class AppRxSchedulersImpl(mainThread : Scheduler) : AppRxSchedulers {
    override val io : Scheduler = Schedulers.io()
    override val main:Scheduler = mainThread
    override val computation:Scheduler = Schedulers.computation()
}