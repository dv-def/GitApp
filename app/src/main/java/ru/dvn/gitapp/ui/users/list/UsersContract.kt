package ru.dvn.gitapp.ui.users.list

import androidx.lifecycle.LiveData
import ru.dvn.gitapp.domain.User

interface UsersContract {

    interface ViewModel {
        val usersLiveData:LiveData<List<User>>
        val errorLiveData:LiveData<Throwable>
        val inProgressLiveData:LiveData<Boolean>

        fun onLoad()
    }
}