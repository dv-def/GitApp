package ru.dvn.gitapp.ui.users.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import coil.load
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.dvn.gitapp.R
import ru.dvn.gitapp.app
import ru.dvn.gitapp.databinding.ActivityUserDetailsBinding
import ru.dvn.gitapp.domain.UserDetails
import ru.dvn.gitapp.domain.UsersRepository

class UserDetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NICK_NAME = "EXTRA_NICK_NAME"
    }
    private lateinit var binding: ActivityUserDetailsBinding

    private lateinit var repository: UsersRepository
    private lateinit var viewModel: UserDetailsViewModel
    private val vmDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = app().appComponent.getUsersRepository()
        viewModel = UserDetailsViewModel(repository)

        intent.extras?.getString(EXTRA_NICK_NAME)?.let { nickName ->
            binding.noDataUserDetails.root.visibility = View.GONE
            initViewModel()
            viewModel.loadDetails(nickName)
        } ?: run {
            Toast.makeText(this, R.string.no_nick_name, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        vmDisposable.dispose()
        super.onDestroy()
    }

    private fun showUserDetails(userDetails: UserDetails) {
        showProgress(inProgress = false)
        binding.userDetailsViewGroup.visibility = View.VISIBLE
        renderDetails(userDetails = userDetails)
    }

    private fun showError(t: Throwable) {
        showProgress(inProgress = false)
        binding.userDetailsViewGroup.visibility = View.GONE
        binding.noDataUserDetails.root.visibility = View.VISIBLE
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(inProgress: Boolean) {
        binding.userDetailsProgress.isVisible = inProgress
        binding.userDetailsViewGroup.isVisible = !inProgress
    }

    private fun renderDetails(userDetails: UserDetails) {
        with(userDetails) {
            avatarUrl?.let {
                binding.userDetailsAvatar.load(it)
            } ?: run {
                binding.userDetailsAvatar.setImageResource(R.drawable.ic_baseline_account_box_24)
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

    private fun initViewModel() {
        vmDisposable.addAll(
            viewModel.userDetails.subscribe { showUserDetails(it) },
            viewModel.errors.subscribe { showError(it) },
            viewModel.inProgress.subscribe { showProgress(it) }
        )
    }
}