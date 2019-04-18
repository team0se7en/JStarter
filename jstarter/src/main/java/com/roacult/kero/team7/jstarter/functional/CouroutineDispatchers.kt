package com.roacult.kero.team7.jstarter.functional

import kotlinx.coroutines.CoroutineDispatcher

/**
 * provide implementation of this interface
 * and injected it in Interactors
 * */
interface CouroutineDispatchers {
    val io:CoroutineDispatcher
    val main:CoroutineDispatcher
    val computaion :CoroutineDispatcher
}