package com.roacult.kero.team7.jstarter.functional

import kotlinx.coroutines.CoroutineDispatcher

interface CouroutineDispatchers {
    val io:CoroutineDispatcher
    val main:CoroutineDispatcher
    val computaion :CoroutineDispatcher
}