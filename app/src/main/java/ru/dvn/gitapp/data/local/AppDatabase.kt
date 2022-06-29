package ru.dvn.gitapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dvn.gitapp.data.local.user.UserDao
import ru.dvn.gitapp.data.local.user.UserDetailsEntity
import ru.dvn.gitapp.data.local.user.UserEntity

@Database(
    entities = [
        UserEntity::class,
        UserDetailsEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DB_NAME = "gitapp"
    }
}