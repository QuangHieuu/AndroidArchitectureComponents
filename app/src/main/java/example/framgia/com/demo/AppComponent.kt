package example.framgia.com.demo

import dagger.Component
import example.framgia.com.demo.data.Repository

@Component(modules = [ApplicationModule::class])
interface AppComponent {

    fun getRepository(): Repository
}
