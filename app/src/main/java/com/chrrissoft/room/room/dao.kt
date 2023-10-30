package com.chrrissoft.room.room

import androidx.room.*

@Entity("users")
data class User(
    @ColumnInfo("id") val id: Long,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("last_name") val lastName: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("dui") val dui: String,
)

@Entity
data class Book(
    val id: Long,
    val name: String,
    val user_id: Long,
)


data class UserPreview(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("last_name") val lastName: String,
)

@Dao
interface UserDao {

    @Query("select name, last_name from users WHERE dui IN (:dui) LIMIT :limit")
    fun usersPreview(limit: Int, dui: List<String>): List<UserPreview>

    @Query("select * from users inner join book on user.id = book.user_id")
    fun userThatHasBooks() : List<User> // or List<Book>

    @Query("select * from users inner join book on user.id = book.user_id")
    fun userThatHasBooksPreview() : List<UserPreview> // or List<"BookPreview">

    @Query("select * from users inner join book on user.id = book.user_id")
    fun userThatHasBooksMap() : Map<UserPreview, List<Book>> // you know chris, you know

    @MapInfo(keyColumn = "name", valueColumn = "name")
    @Query("select users.name, books.name from users inner join book on user.id = book.user_id")
    fun userThatHasBooksMapColum() : Map<String, List<String>>

}
