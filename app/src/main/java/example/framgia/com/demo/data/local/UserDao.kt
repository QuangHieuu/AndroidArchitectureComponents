package example.framgia.com.demo.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import example.framgia.com.demo.data.model.User
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY uid")
    fun getAll(): Flowable<List<User>>

    @Query("SELECT * FROM user WHERE uid = (:userIds)")
    fun findUserById(userIds: Int): User

    @Query(
        "SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String, last: String): Flowable<User>

    @Query("UPDATE user SET first_name = :first, last_name = :last WHERE uid = :id")
    fun updateUser(first: String, last: String, id: Int)

    @Insert
    fun insertAll(user: User)

    @Delete
    fun delete(user: User)
}
