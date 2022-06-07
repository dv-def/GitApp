package ru.dvn.gitapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.dvn.gitapp.app
import ru.dvn.gitapp.databinding.ActivityMainBinding
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.ui.users.UsersAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val repository: GithubRepository by lazy { app().repository }
    private val adapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        binding.buttonMainLoadUsers.setOnClickListener {
            lifecycle.coroutineScope.launch {
                showProgressBar()
                repository.getUsers(
                    onSuccess = { userList ->
                        hideProgressBar()
                        adapter.setData(userList)
                    },
                    onError = { throwable ->
                        hideProgressBar()
                        Toast.makeText(this@MainActivity, throwable.message, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        initUsersRecyclerView()
    }

    private fun initUsersRecyclerView() {
        binding.recyclerViewMainUsers.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun showProgressBar() {
        binding.progressMain.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressMain.visibility = View.GONE
    }
}