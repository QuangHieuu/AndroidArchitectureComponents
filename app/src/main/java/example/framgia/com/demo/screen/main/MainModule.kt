package example.framgia.com.demo.screen.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import dagger.Module
import dagger.Provides
import example.framgia.com.demo.R
import example.framgia.com.demo.databinding.ActivityMainBinding

@Module
class MainModule(private val mainActivity: MainActivity) {

    @Provides
    fun provideMainBinding(): ActivityMainBinding {
        return DataBindingUtil.setContentView(mainActivity, R.layout.activity_main)
    }

    @Provides
    fun provideMainViewModel(): MainViewModel {
        return ViewModelProviders.of(mainActivity).get(MainViewModel::class.java)
    }

    @Provides
    fun provideLifecycleRegistry(): LifecycleRegistry {
        val lifecycleRegistry = LifecycleRegistry(mainActivity)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
        return lifecycleRegistry
    }

    @Provides
    fun provideCustomLifecycle(): CustomLifecycle {
        return CustomLifecycle(
            provideLifecycleRegistry(),
            provideMainViewModel()
        )
    }

}
