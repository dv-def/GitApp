package ru.dvn.gitapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dvn.gitapp.app
import ru.dvn.gitapp.databinding.ActivityMainBinding
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User
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
            loadUsers()
        }

        initUsersRecyclerView()
    }

    private fun loadUsers() {
        changeViewVisibility(view = binding.progressMain, shouldShow = true)
        repository.getUsers(
            onSuccess = { userList ->
                onUsersLoaded(userList)
            },
            onError = { throwable ->
               onDataError(throwable)
            }
        )
    }

    private fun initUsersRecyclerView() {
        binding.recyclerViewMainUsers.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun onUsersLoaded(users: List<User>) {
        changeViewVisibility(view = binding.progressMain, shouldShow = false)
        changeViewVisibility(view = binding.buttonMainLoadUsers, shouldShow = false)
        adapter.setData(users)
    }

    private fun onDataError(throwable: Throwable) {
        changeViewVisibility(view = binding.progressMain, shouldShow = false)
        Toast.makeText(this@MainActivity, throwable.message, Toast.LENGTH_SHORT).show()
    }

    private fun changeViewVisibility(view: View, shouldShow: Boolean) {
        view.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

}