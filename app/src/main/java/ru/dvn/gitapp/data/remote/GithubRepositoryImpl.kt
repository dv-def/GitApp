package ru.dvn.gitapp.data.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dvn.gitapp.data.remote.user.*
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.domain.UserDetails

class GithubRepositoryImpl(private val githubApi: GithubApi) : GithubRepository {
    override fun getUsers(
        onSuccess: (List<User>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        githubApi.getUsers().enqueue(object : Callback<List<UserDTO>> {
            override fun onResponse(call: Call<List<UserDTO>>, response: Response<List<UserDTO>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        onSuccess.invoke(result.map { dto -> dto.toUser() })
                    } else {
                        onError.invoke(Exception("Список пользователей не получен"))
                    }
                } else {
                    onError.invoke(Exception("Не удалось получить список пользователей"))
                }
            }

            override fun onFailure(call: Call<List<UserDTO>>, t: Throwable) {
                onError.invoke(t)
            }
        })
    }

    override fun getUserDetails(
        nickName: String,
        onSuccess: (UserDetails) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        githubApi.getUserDetails(nickName).enqueue(object : Callback<UserDetailsDTO> {
            override fun onResponse(
                call: Call<UserDetailsDTO>,
                response: Response<UserDetailsDTO>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        onSuccess.invoke(result.toUserDetails())
                    } else {
                        onError.invoke(Exception("Данные о пользователе не получены"))
                    }
                } else {
                    onError.invoke(Exception("Не удалось загрузить информацию о пользователе"))
                }
            }

            override fun onFailure(call: Call<UserDetailsDTO>, t: Throwable) {
                onError.invoke(t)
            }
        })
    }
}