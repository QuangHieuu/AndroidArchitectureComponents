package example.framgia.com.demo

import android.app.Application

class MainApplication : Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

    }

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().applicationModule(ApplicationModule(this)).build()
        }
        return appComponent!!
    }
}