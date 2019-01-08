package example.framgia.com.demo.data

import example.framgia.com.demo.data.model.User
import io.reactivex.Flowable

class Repository(private var local: DataRepository.Local) {

    fun getAllUser(): Flowable<List<User>> {
        return local.getAllUser()
    }

    fun insertUser(user: User) {
        local.insertUser(user)
    }

    fun updateUser(first: String, last: String, id: Int) {
        local.updateUser(first, last, id)
    }

    companion object {

        private var instance: Repository? = null

        fun getInstance(local: DataRepository.Local): Repository {
            if (instance == null) {
                instance = Repository(local)
            }
            return instance!!
        }
    }


}