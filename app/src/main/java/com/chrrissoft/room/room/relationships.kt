package com.chrrissoft.room.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserBookDao {
    @Query("""SELECT user.name AS userName, book.name AS bookName
               FROM user, book WHERE user.id = book.user_id""")
    fun loadUserAndBookNames(): Flow<List<UserBook>>

    @Query("SELECT * FROM user JOIN book ON user.id = book.user_id")
    fun loadUserAndBookNamesMultiMap(): Map<User, List<Book>>
}

data class UserBook(val userName: String?, val bookName: String?)


@Entity("users")
data class SomeUser(
    val id: Long,
    val name: String,
    @ColumnInfo("house_address") @Embedded(prefix = "house") val houseAddress: Address,
    @ColumnInfo("work_address") @Embedded(prefix = "work") val workAddress: Address,
)

/**
 * under hood: users ->
 * fields: id, name, house_street, house_state, house_city, house_post_code,
 * work_street, work_state, work_city, work_post_code
 * */

data class Address(
    val street: String?,
    val state: String?,
    val city: String?,
    @ColumnInfo(name = "post_code") val postCode: Int,
    // we can also add other data classes here
)

/*****************************  one to one *****************************/

@Entity
data class OneToOneUser(
    @PrimaryKey
    val id: Long,
    val name: String,
)

@Entity
data class UserDui(
    @PrimaryKey
    val id: Long,
    val number: Long,
    // more fields..
)

data class UserHisAndDui(
    @Embedded
    val user: OneToOneUser,
    @Relation(
        parentColumn = "id", // one to one user table
        entityColumn = "id", // user dui table
    )
    val dui: UserDui,
)

data class DuiAndHisUser(
    @Embedded
    val dui: UserDui,
    @Relation(
        parentColumn = "id", // user dui table
        entityColumn = "id", // one to one user table
    )
    val user: OneToOneUser,
)

interface OneToOneDao {
    @Transaction
    @Query("select * from OneToOneUser;")
    fun getUsersAndTheirsDui(): Flow<List<UserHisAndDui>>

    @Transaction
    @Query("select * from UserDui;")
    fun getDuiAndTheirsUser(): Flow<List<DuiAndHisUser>>

}


/*****************************  one to many *****************************/

@Entity("one_to_many_users")
data class OneToManyUser(
    @PrimaryKey
    val id: Long,
    val name: String,
)

@Entity("playlists")
data class Playlist(
    @PrimaryKey
    val id: Long,
    val user_id: Long,
    val title: String,
)

data class UserAndTheisPlaylists(
    @Embedded val user: OneToManyUser,
    @Relation(parentColumn = "id", entityColumn = "user_id")
    val playlists: List<Playlist>,
)

interface OneToManyDao {
    @Transaction
    @Query("SELECT * FROM one_to_many_users")
    fun getUsersAndTheisPlaylists(): List<UserAndTheisPlaylists>
}


/*****************************  many to many *****************************/

@Entity("songs")
data class Song(
    @PrimaryKey val id: Long,
    val name: String,
)

@Entity("playlists_2")
data class Playlist2(
    @PrimaryKey val id: Long,
    val title: String,
)

// one song cannot be in the seme playlist more than once
// and one playlist cannot have the seme song more than once
@Entity(primaryKeys = ["song_id", "playlist_id"])
data class PlaylistSongCrossRef(
    @ColumnInfo("song_id") val songId: Long,
    @ColumnInfo("playlist_id") val playlistId: Long,
)

data class SongAndTheirPlaylists(
    @Embedded val song: Song,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val playlist: List<Playlist2>,
)

data class PlaylistAndTheirSongs(
    @Embedded val song: Playlist2,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val playlist: List<Song>,
)

interface ManyToManyDao {

    @Query("SELECT * FROM songs")
    fun getSongsAndTheirPlaylists(): Flow<List<SongAndTheirPlaylists>>

    @Query("SELECT * FROM playlists_2")
    fun getPlaylistsAndTheirSongs(): Flow<List<PlaylistAndTheirSongs>>
}



/*****************************  many to many with 3 or more tables *****************************/
// TODO











