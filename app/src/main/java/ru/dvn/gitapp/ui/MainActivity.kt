package ru.dvn.gitapp.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dvn.gitapp.app
import ru.dvn.gitapp.databinding.ActivityMainBinding
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.ui.users.UsersAdapter
import ru.dvn.gitapp.ui.users.UsersContract
import ru.dvn.gitapp.ui.users.UsersPresenter

class MainActivity : AppCompatActivity(), UsersContract.View {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UsersAdapter()

    private lateinit var presenter: UsersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        presenter = restorePresenter()
        presenter.attach(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): UsersContract.Presenter {
        return presenter
    }

    override fun showUsers(users: List<User>) {
        showProgress(inProgress = false)
        binding.buttonMainLoadUsers.visibility = View.GONE
        adapter.setData(users)
    }

    override fun showError(throwable: Throwable) {
        showProgress(inProgress = false)
        Toast.makeText(this@MainActivity, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(inProgress: Boolean) {
        binding.progressMain.visibility = if (inProgress) View.VISIBLE else View.GONE
    }

    private fun restorePresenter(): UsersContract.Presenter {
        return lastCustomNonConfigurationInstance as? UsersContract.Presenter
            ?: UsersPresenter(app().repository)
    }

    private fun initUi() {
        binding.buttonMainLoadUsers.setOnClickListener {
            presenter.onLoad()
        }

        initUsersRecyclerView()
    }

    private fun initUsersRecyclerView() {
        binding.recyclerViewMainUsers.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}