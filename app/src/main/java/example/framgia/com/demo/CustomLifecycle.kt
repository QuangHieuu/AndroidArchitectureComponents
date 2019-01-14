package example.framgia.com.demo

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

class CustomLifecycle(private val lifecycle: Lifecycle, private val mainViewModel: MainViewModel) : LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED))
            mainViewModel.onCreate()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        mainViewModel.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        mainViewModel.onStop()
    }
}