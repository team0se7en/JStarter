package com.roacult.kero.team7.jstarter.functional

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class AppCoroutineDispatchers  : CouroutineDispatchers{
    override val io: CoroutineDispatcher = Dispatchers.IO
    @ExperimentalCoroutinesApi
    override val computaion: CoroutineDispatcher = Dispatchers.Unconfined
    override val main: CoroutineDispatcher = Dispatchers.Main
}