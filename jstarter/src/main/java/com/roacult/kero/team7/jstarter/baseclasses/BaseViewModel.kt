package  com.roacult.kero.team7.jstarter.baseclasses

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.roacult.kero.team7.jstarter.exception.Failure
import com.roacult.kero.team7.jstarter.interactors.ObservableEitherInteractor
import com.roacult.kero.team7.jstarter.interactors.ObservableInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


abstract  class BaseViewModel <S: State> ( initialState:S ) : ViewModel() {

    private val state:MutableLiveData<S> by lazy {
        val liveData:MutableLiveData<S> = MutableLiveData()
        liveData.value=initialState
        liveData
    }

    private val job = Job()
    private  val disposable:CompositeDisposable = CompositeDisposable()


    /**
     * scope to launch interactors
     * */
    protected val scope  = CoroutineScope(Dispatchers.Main+job)

    /**
     * this method will change state in live date
     * */
    protected fun setState( stateChanger :S.()->S){
        state.value = state.value?.stateChanger()
    }


    /**
     * observe state in live date
     * */
    fun observe(lifecycleOwner: LifecycleOwner, observer : (S) -> Unit ) {
        state.observe(lifecycleOwner, Observer(observer))
    }

    /**
     * this method will not change state
     * it's only used to handle current value of  state
     * */
    fun withState(stateHandler : (S) -> Unit){
        stateHandler(state.value!!)
    }

    override fun onCleared() {
        super.onCleared()
        if(job.isActive){
            job.cancel()
        }
        disposable.dispose()
    }

    /**
     * launch observable either interactor
     * @param interactor interactor you want launch
     * @param errorHandler callback of errors
     * @param dataHandler callback of result
     * @return Disposable to dispose stream
     *
     * Note : all interactors launched by this method
     * will automatically disposed when viewModel destroyed
     * */
    protected fun <P,R,F:Failure> launchEitherObservableInteractor(interactor: ObservableEitherInteractor<R, P, F>,
                                                                          param : P ,
                                                                          errorHandler : (F?) -> Unit,
                                                                          dataHandler:(R)->Unit) :  Disposable{
        val dispos = interactor.observe(param) {
            it.either(errorHandler,dataHandler)
        }
        disposable.add(dispos)
        return dispos
    }

    protected fun  <P, Type> launchObservableInteractor(interactor: ObservableInteractor<Type, P>,
                                                        param:P,
                                                        errorHandler :(Throwable)->Unit,
                                                        dataHandler:(Type)->Unit) : Disposable{
        val dispos = interactor.observe(param, errorHandler, dataHandler)
        disposable.add(dispos)
        return dispos
    }
}

