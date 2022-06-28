package ru.dvn.gitapp.data.fake

import io.reactivex.rxjava3.core.Single
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.domain.UserDetails

class FakeGitHubRepositoryImpl : GithubRepository {
    companion object {
        private const val DELAY = 2000L
    }

    private val data = listOf(
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

    override fun getUsers(): Single<List<User>> = Single.just(data)

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