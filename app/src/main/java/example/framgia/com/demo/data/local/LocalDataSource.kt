package example.framgia.com.demo.data.local

import example.framgia.com.demo.data.DataRepository
import example.framgia.com.demo.data.model.User
import io.reactivex.Flowable

class LocalDataSource(private var userDao: UserDao) : DataRepository.Local {

    override fun updateUser(first: String, last: String, id: Int) {
        userDao.updateUser(first, last, id)
    }

    override fun insertUser(user: User) {
        userDao.insertAll(user)
    }

    override fun getAllUser(): Flowable<List<User>> {
        return userDao.getAll()
    }

    companion object {

        private var instance: LocalDataSource? = null

        fun getInstance(userDao: UserDao): LocalDataSource {
            if (instance == null) {
                instance = LocalDataSource(userDao)
            }
            return instance!!
        }
    }


}