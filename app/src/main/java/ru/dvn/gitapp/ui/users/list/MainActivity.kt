package ru.dvn.gitapp.ui.users.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dvn.gitapp.app
import ru.dvn.gitapp.databinding.ActivityMainBinding
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.ui.users.details.UserDetailsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    private lateinit var viewModel: UsersContract.ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = restoreViewModel()
        initUi()
    }

    override fun onRetainCustomNonConfigurationInstance(): UsersContract.ViewModel {
        return viewModel
    }

    private fun showUsers(users: List<User>) {
        showProgress(inProgress = false)
        binding.buttonMainLoadUsers.visibility = View.GONE
        adapter.setData(users)
    }

    private fun showError(throwable: Throwable) {
        showProgress(inProgress = false)
        Toast.makeText(this@MainActivity, throwable.message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(inProgress: Boolean) {
        binding.progressMain.visibility = if (inProgress) View.VISIBLE else View.GONE
    }

    private fun restoreViewModel(): UsersContract.ViewModel {
        return lastCustomNonConfigurationInstance as? UsersContract.ViewModel
            ?: UsersViewModel(app().repository)
    }

    private fun initUi() {
        viewModel.usersLiveData.observe(this) {
            showUsers(it)
        }

        viewModel.errorLiveData.observe(this) {
            showError(it)
        }

        viewModel.inProgressLiveData.observe(this) {
            showProgress(it)
        }

        binding.buttonMainLoadUsers.setOnClickListener {
            viewModel.onLoad()
        }

        initUsersRecyclerView()
    }

    private fun initUsersRecyclerView() {
        adapter = UsersAdapter { nickName ->
            goToDetails(nickName)
        }
        binding.recyclerViewMainUsers.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun goToDetails(nickName: String) {
        startActivity(Intent(this, UserDetailsActivity::class.java).apply {
            putExtra(UserDetailsActivity.EXTRA_NICK_NAME, nickName)
        })
    }
}