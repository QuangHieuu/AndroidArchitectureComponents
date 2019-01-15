package example.framgia.com.demo.screen.main

import android.arch.lifecycle.LifecycleRegistry
import dagger.Component
import example.framgia.com.demo.AppComponent
import example.framgia.com.demo.databinding.ActivityMainBinding

@Component(dependencies = [AppComponent::class], modules = [MainModule::class])
interface MainComponent {

    fun getMainBinding(): ActivityMainBinding

    fun getMainViewModel(): MainViewModel

    fun getLifecycleRegistry(): LifecycleRegistry

    fun getCustomLifecycle(): CustomLifecycle

    fun inject(activity: MainActivity)
}
