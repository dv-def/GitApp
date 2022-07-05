package ru.dvn.gitapp.ui.users.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.dvn.gitapp.app
import ru.dvn.gitapp.databinding.ActivityMainBinding
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.ui.users.details.UserDetailsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    private lateinit var viewModel: UsersContract.ViewModel
    private val vmDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = restoreViewModel()

        initUsersRecyclerView()

        vmDisposable.addAll(
            viewModel.users.subscribe { showUsers(it) },
            viewModel.errors.subscribe { showError(it) },
            viewModel.inProgress.subscribe { showProgress(it) },
            viewModel.onOpenDetails.subscribe { goToDetails(it) },
            binding.buttonMainLoadUsers.btnObservable.subscribe { loadData() }
        )

//        binding.buttonMainLoadUsers.setOnClickListener {
//            viewModel.onLoad()
//        }
    }

    override fun onDestroy() {
        vmDisposable.dispose()
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): UsersContract.ViewModel {
        return viewModel
    }

    private fun loadData() {
        viewModel.onLoad()
    }

    private fun showUsers(users: List<User>) {
        showProgress(inProgress = false)
        if (users.isEmpty()) {
            binding.recyclerViewMainUsers.visibility = View.GONE
            binding.noDataUser.root.visibility = View.VISIBLE
        } else {
            adapter.setData(users)

            binding.recyclerViewMainUsers.visibility = View.VISIBLE
            binding.noDataUser.root.visibility = View.GONE
        }
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
            ?: UsersViewModel(app().mainRepository)
    }

    private fun initUsersRecyclerView() {
        adapter = UsersAdapter { nickName ->
            viewModel.onClickUser(nickName)
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