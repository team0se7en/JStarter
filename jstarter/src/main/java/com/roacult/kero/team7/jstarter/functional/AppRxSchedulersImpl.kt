package com.roacult.kero.team7.jstarter.functional

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class AppRxSchedulersImpl(mainThread : Scheduler) : AppRxSchedulers {
    override val io : Scheduler = Schedulers.io()
    override val main:Scheduler = mainThread
    override val computation:Scheduler = Schedulers.computation()
}