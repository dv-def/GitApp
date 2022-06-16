package ru.dvn.gitapp.data.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dvn.gitapp.data.remote.user.GithubApi
import ru.dvn.gitapp.data.remote.user.UserDTO
import ru.dvn.gitapp.data.remote.user.toUser
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User

class GithubRepositoryImpl(private val githubApi: GithubApi) : GithubRepository {
    override fun getUsers(
        onSuccess: (List<User>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        githubApi.getUsers().enqueue(object : Callback<List<UserDTO>> {
            override fun onResponse(call: Call<List<UserDTO>>, response: Response<List<UserDTO>>) {
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    onSuccess.invoke(result.map { dto -> dto.toUser() })
                } else {
                    onError.invoke(Exception("Не удалось получить список пользователей"))
                }
            }

            override fun onFailure(call: Call<List<UserDTO>>, t: Throwable) {
                onError.invoke(t)
            }
        })
    }
}