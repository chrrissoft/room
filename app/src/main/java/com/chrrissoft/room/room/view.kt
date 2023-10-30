package com.chrrissoft.room.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity("user_view")
data class ViewUser(
    @PrimaryKey
    val id: Long,
    val name: String,
    val departmentId: Long
)

@Entity
data class Department(
    @PrimaryKey
    val id: Long,
    val name: String
)

@DatabaseView("SELECT user_view.id, user_view.name, user_view.departmentId," +
        "department.name AS departmentName FROM user " +
        "INNER JOIN Department ON user_view.departmentId = Department.id")
data class UserDetail(
    val id: Long,
    val name: String?,
    val departmentId: Long,
    val departmentName: String?
)

interface ViewDao {
    fun getUserDetails() : Flow<UserDetail>
}

@Database(entities = [User::class], views =[UserDetail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun viewDao(): ViewDao
}
