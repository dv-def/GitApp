package ru.dvn.gitapp.ui.users.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import coil.load
import ru.dvn.gitapp.R
import ru.dvn.gitapp.app
import ru.dvn.gitapp.databinding.ActivityUserDetailsBinding
import ru.dvn.gitapp.domain.UserDetails

class UserDetailsActivity : AppCompatActivity(), UserDetailsContract.View {
    companion object {
        const val EXTRA_NICK_NAME = "EXTRA_NICK_NAME"
    }
    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var presenter: UserDetailsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getString(EXTRA_NICK_NAME)?.let { nickName ->
            presenter = restorePresenter(nickName)
            presenter.attach(this)
        } ?: run {
            Toast.makeText(this, R.string.no_nick_name, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): UserDetailsContract.Presenter {
        return presenter
    }

    override fun showUserDetails(userDetails: UserDetails) {
        showProgress(inProgress = false)
        binding.userDetailsViewGroup.visibility = View.VISIBLE
        renderDetails(userDetails = userDetails)
    }

    override fun showError(t: Throwable) {
        showProgress(inProgress = false)
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(inProgress: Boolean) {
        binding.userDetailsProgress.isVisible = inProgress
        binding.userDetailsViewGroup.isVisible = !inProgress
    }

    private fun restorePresenter(nickName: String): UserDetailsContract.Presenter {
        return lastCustomNonConfigurationInstance as? UserDetailsContract.Presenter
            ?: UserDetailsPresenter(app().repository, nickName)
    }

    private fun renderDetails(userDetails: UserDetails) {
        with(userDetails) {
            avatarUrl?.let {
                binding.userDetailsAvatar.load(it)
            }
            name?.let {
                binding.userDetailsName.text = it
            } ?: run { binding.userDetailsName.visibility = View.GONE }

            publicRepos?.let {
                binding.userDetailsPublicReposValue.text = it.toString()
            } ?: run {
                binding.userDetailsPublicReposTitle.visibility = View.GONE
                binding.userDetailsPublicReposValue.visibility = View.GONE
            }

            publicGists?.let {
                binding.userDetailsPublicGistsValue.text = it.toString()
            } ?: run {
                binding.userDetailsPublicGistsValue.visibility = View.GONE
                binding.userDetailsPublicGistsValue.visibility = View.GONE
            }

            followers?.let {
                binding.userDetailsPublicFollowersValue.text = it.toString()
            } ?: run {
                binding.userDetailsPublicFollowersValue.visibility = View.GONE
                binding.userDetailsPublicFollowersValue.visibility = View.GONE
            }

            following?.let {
                binding.userDetailsPublicFollowingValue.text = following.toString()
            } ?: run {
                binding.userDetailsPublicFollowingValue.visibility = View.GONE
                binding.userDetailsPublicFollowingValue.visibility = View.GONE
            }
        }
    }
}