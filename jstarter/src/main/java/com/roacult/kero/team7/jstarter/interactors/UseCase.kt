package com.roacult.kero.team7.jstarter.interactors

import android.util.Log
import com.roacult.kero.team7.jstarter.exception.Failure
import com.roacult.kero.team7.jstarter.functional.AppRxSchedulers
import com.roacult.kero.team7.jstarter.functional.CouroutineDispatchers
import com.roacult.kero.team7.jstarter.functional.Either
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.*

/**
 * interactor runs in background Couroutine and return
 * either result or Failure
 * */

abstract class EitherInteractor<in P, out R , out F : Failure>(dispatcher: CouroutineDispatchers) {
    val dispatcher: CoroutineDispatcher = dispatcher.io
    val resultDispatcher :CoroutineDispatcher = dispatcher.main
    abstract suspend operator fun invoke(executeParams: P): Either<F, R>
}


/**
 * interactor runs in background Couroutine and return result
 * this interctore is used in cases where there is no possible failures
 * */

abstract class Interactor<in P , out R >(dispatcher: CouroutineDispatchers){
    val dispatcher: CoroutineDispatcher = dispatcher.io
    val resultDispatcher :CoroutineDispatcher = dispatcher.main
    abstract suspend operator fun invoke(executeParams: P):R
}

/**
 * interactore runs in background thread and return a stream
 * of Either values that's could be either @param R
 * or @param Failure if failure is null that's mean unknown failure
 * the observable returned by this interactor will never invoke
 * onError methode the claaback will be only in onNext methode
 * */

abstract class ObservableEitherInteractor<R , in P , F:Failure>(private val schedulers:AppRxSchedulers){
    private val subject = BehaviorSubject.create<Either<F? , R>>()
    protected abstract fun buildObservable(p:P):Observable< Either<F? , R>>

    fun observe(p : P , handler : (Either<F? , R>) -> Unit ) : Disposable {
        buildObservable(p).onErrorReturn {
            Log.v("unknown failure",it.message)
            it.printStackTrace()
            Either.Left<F?>(null)
        }.subscribe(subject)
        return subject.subscribeOn(schedulers.io).observeOn(schedulers.main)
            .subscribe(handler)
    }
}

/**
 * interctore runs in background thread and return a stream
 * of values values of type @param Type
 * this interactor is used like a normal observable
 * */

abstract class ObservableInteractor<Type , in Params>(private val schedulers:AppRxSchedulers){

    protected abstract fun buildObservable(p:Params):Observable< Type>

    fun observe(p:Params, FailureObserver:(e:Throwable)->Unit , SuccesObserver:(t:Type)->Unit): Disposable {
        return buildObservable(p).subscribeOn(schedulers.io).observeOn(schedulers.main)
            .subscribe(SuccesObserver, FailureObserver)
    }
}

/**
 * an extension  function to launch Either Interactor
 * frome viewModel scope
 * */

fun <P, R, T:Failure> CoroutineScope.launchInteractor(interactor: EitherInteractor<P, R, T>, param: P, OnResult:(Either<T, R>)->Unit): Job {
    val  job = async(interactor.dispatcher) { interactor(param) }
    return launch(interactor.resultDispatcher) { OnResult(job.await()) }
}


/**
 * an extension  function to launch simple Interactor
 * frome viewModel scope
 * */

fun <P , R> CoroutineScope.launchInteractor(interactor: Interactor<P, R>, param: P, onResult:(R)->Unit):Job{
    val job = async(context = interactor.dispatcher){interactor(param)}
    return launch (interactor.resultDispatcher){
        onResult(job.await())}
}


/**
 * this classe mean no parametre will be supplied to interactors
 * or no result  waited by interactor
 * */

class None