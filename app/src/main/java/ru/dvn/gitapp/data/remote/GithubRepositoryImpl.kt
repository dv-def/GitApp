package ru.dvn.gitapp.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dvn.gitapp.data.remote.user.*
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.domain.UserDetails

class GithubRepositoryImpl(private val githubApi: GithubApi) : GithubRepository {
    override fun getUsers(): Single<List<User>> {
        return githubApi.getUsers().map { usersList ->
            usersList.map { dto -> dto.toUser() }
        }
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