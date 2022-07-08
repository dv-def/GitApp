package ru.dvn.gitapp.ui.users.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.dvn.gitapp.R
import ru.dvn.gitapp.databinding.UserItemBinding
import ru.dvn.gitapp.domain.User

class UsersViewHolder(
    parent: ViewGroup,
    private val onItemClick: (nickName: String) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
) {
    private val binding = UserItemBinding.bind(itemView)

    fun bind(user: User) {
        user.avatarUrl?.let {
            binding.userItemAvatar.load(user.avatarUrl) {
                placeholder(R.drawable.ic_baseline_account_circle_24)
            }
        } ?: run {
            binding.userItemAvatar.setImageResource(R.drawable.ic_baseline_account_circle_24)
        }

        binding.userItemName.text = user.login
        binding.userItemId.text = user.id.toString()

        itemView.setOnClickListener {
            onItemClick.invoke(user.login)
        }
    }
}