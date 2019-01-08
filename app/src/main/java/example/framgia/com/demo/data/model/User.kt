package example.framgia.com.demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?
)
