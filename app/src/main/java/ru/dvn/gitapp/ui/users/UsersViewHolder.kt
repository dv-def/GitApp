package ru.dvn.gitapp.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.dvn.gitapp.R
import ru.dvn.gitapp.databinding.UserItemBinding
import ru.dvn.gitapp.domain.models.User

class UsersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
) {
    private val binding = UserItemBinding.bind(itemView)

    fun bind(user: User) {
        binding.userItemAvatar.load(user.avatarUrl)
        binding.userItemName.text = user.login
        binding.userItemId.text = user.id.toString()
    }
}