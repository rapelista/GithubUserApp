package com.gvstang.dicoding.latihan.githubuser.adapter.recyclerview

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gvstang.dicoding.latihan.githubuser.databinding.ItemUserBinding
import com.gvstang.dicoding.latihan.githubuser.util.data.User
import com.gvstang.dicoding.latihan.githubuser.view.user_detail.UserDetailActivity


class UserListAdapter(private val listUser: ArrayList<User>): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private lateinit var binding: ItemUserBinding

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (username, avatarUrl) = listUser[position]
        binding.tvItemUsername.text = username
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .circleCrop()
            .into(binding.ivItemAvatar)
        binding.itemUser.setOnClickListener {
            val context = holder.itemView.context as Activity
            val intent = Intent(context, UserDetailActivity::class.java)
                .putExtra("username", username)
                .putExtra("avatarUrl", avatarUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}