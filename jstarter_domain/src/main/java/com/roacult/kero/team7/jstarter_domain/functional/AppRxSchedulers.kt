package com.roacult.kero.team7.jstarter_domain.functional

import io.reactivex.Scheduler

/**
 * provide implementation of this interface
 * and injected it in Interactors
 * */
interface AppRxSchedulers {
     val io : Scheduler
     val main :Scheduler
    val computation:Scheduler
}