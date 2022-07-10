package ru.dvn.gitapp.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    fun getUsersList(): Single<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(users: List<UserEntity>)

    @Query("SELECT * FROM ${UserDetailsEntity.TABLE_NAME} WHERE login = :nickname")
    fun getDetails(nickname: String): Single<UserDetailsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetails(userDetailsEntity: UserDetailsEntity)
}