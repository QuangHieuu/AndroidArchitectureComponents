package example.framgia.com.demo.data

import example.framgia.com.demo.data.model.User
import io.reactivex.Flowable

interface DataRepository {

    interface Local {
        fun getAllUser(): Flowable<List<User>>

        fun insertUser(user: User)

        fun updateUser(first: String, last: String, id: Int)
    }
}