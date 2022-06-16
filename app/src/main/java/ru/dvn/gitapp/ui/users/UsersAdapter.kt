package ru.dvn.gitapp.ui.users

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dvn.gitapp.domain.User

class UsersAdapter : RecyclerView.Adapter<UsersViewHolder>() {
    private val users = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(parent)

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getUser(position))
    }

    override fun getItemCount() = users.size

    fun setData(userList: List<User>) {
        users.clear()
        users.addAll(userList)
        notifyDataSetChanged()
    }

    private fun getUser(position: Int) = users[position]
}