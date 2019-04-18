package com.roacult.kero.team7.jstarter.functional

import kotlinx.coroutines.CoroutineDispatcher

class CouroutineDispatchersImpl(override val io: CoroutineDispatcher, override val computaion: CoroutineDispatcher,
                                override val main: CoroutineDispatcher): CouroutineDispatchers