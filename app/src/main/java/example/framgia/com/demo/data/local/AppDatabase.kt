package example.framgia.com.demo.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import example.framgia.com.demo.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context, AppDatabase::class.java,
                    "database-name"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
            return instance!!
        }
    }

}