package ru.dvn.gitapp.ui.users.details

import androidx.lifecycle.LiveData
import ru.dvn.gitapp.domain.UserDetails

interface UserDetailsContract {

    interface ViewModel {
        val userDetailsLiveData: LiveData<UserDetails>
        val errorLiveData: LiveData<Throwable>
        val inProgressLiveData: LiveData<Boolean>

        fun loadDetails()
    }
}