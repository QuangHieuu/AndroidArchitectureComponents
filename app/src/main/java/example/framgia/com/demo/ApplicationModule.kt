package example.framgia.com.demo

import android.content.Context
import dagger.Module
import dagger.Provides
import example.framgia.com.demo.data.Repository
import example.framgia.com.demo.data.local.AppDatabase
import example.framgia.com.demo.data.local.LocalDataSource


@Module
class ApplicationModule(private val context: Context) {

    @Provides
    fun provideApplicationContext(): Context {
        return context
    }

    @Provides
    fun provideRoomDatabase(): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSource.getInstance(provideRoomDatabase().userDao())
    }

    @Provides
    fun provideRepository(): Repository {
        return Repository.getInstance(provideLocalDataSource())
    }

}
