package ru.dvn.gitapp.data.fake

import android.os.Handler
import android.os.Looper
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.domain.UserDetails

class FakeGitHubRepositoryImpl : GithubRepository {
    companion object {
        private const val DELAY = 2000L
    }

    override fun getUsers(
        onSuccess: (List<User>) -> Unit,
        onError: (t: Throwable) -> Unit,
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            onSuccess.invoke(
                listOf(
                    User(
                        id = 1,
                        login = "mojombo",
                        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4"
                    ),
                    User(
                        id = 2,
                        login = "defunkt",
                        avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4"
                    ),
                    User(
                        id = 3,
                        login = "pjhyett",
                        avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4"
                    )
                )
            )
        }, DELAY)
    }

    override fun getUserDetails(
        nickName: String,
        onSuccess: (UserDetails) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        onSuccess.invoke(
            UserDetails(
                login = "defunkt",
                avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4",
                name = "Chris Wanstrath",
                publicRepos = 107,
                publicGists = 273,
                followers = 21444,
                following = 210
            )
        )
    }
}