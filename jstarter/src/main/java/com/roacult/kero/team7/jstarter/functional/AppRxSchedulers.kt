package com.roacult.kero.team7.jstarter.functional

import io.reactivex.Scheduler


interface AppRxSchedulers {
     val io : Scheduler
     val main :Scheduler
    val computation:Scheduler
}