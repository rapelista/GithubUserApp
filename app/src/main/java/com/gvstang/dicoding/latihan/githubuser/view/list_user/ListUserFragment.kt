package com.gvstang.dicoding.latihan.githubuser.view.list_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvstang.dicoding.latihan.githubuser.R
import com.gvstang.dicoding.latihan.githubuser.adapter.recyclerview.UserListAdapter
import com.gvstang.dicoding.latihan.githubuser.databinding.FragmentListUserBinding
import com.gvstang.dicoding.latihan.githubuser.util.data.User

class ListUserFragment : Fragment() {

    private lateinit var listUserViewModel: ListUserViewModel

    private var _binding: FragmentListUserBinding? = null
    private val binding get() = _binding!!

    private var listUser = ArrayList<User>()

    private val adapter = UserListAdapter(listUser)
    private val layoutManager = LinearLayoutManager(context)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setResult(arguments)

        listUserViewModel.listUser.observe(viewLifecycleOwner) {
            updateRecyclerView(it)
        }
    }

    private fun updateRecyclerView(data: java.util.ArrayList<User>) {
        binding.apply {
            if(data.isEmpty()) {
                tvZero.isVisible = true

            } else {
                listUser.clear()

                data.map { user ->
                    listUser.apply {
                        add(User(user.username, user.avatarUrl, id = user.id))
                        sortBy { it.id }
                    }
                }

                binding.rvDetailListUser.adapter = UserListAdapter(listUser)
                rvDetailListUser.isVisible = true
            }

            loadingListUser.isVisible = false
        }


    }

    private fun setupRecyclerView() {
        binding.apply {
            rvDetailListUser.layoutManager = layoutManager
            rvDetailListUser.adapter = adapter
        }
    }

    private fun setResult(arguments: Bundle?) {
        val position = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME).toString()
        val followers = arguments?.getInt(ARG_FOLLOWERS, 0)
        val followings = arguments?.getInt(ARG_FOLLOWINGS, 0)

        binding.tvZero.text = getString(R.string.ff_detail, when(position) {
            0 -> {
                listUserViewModel.getFollowers(username, followers)
                "follower"
            }
            1 -> {
                listUserViewModel.getFollowings(username, followings)
                "following"
            }
            else -> ""
        })
    }

    private fun setupViewModel() {
        listUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ListUserViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
        const val ARG_FOLLOWERS = "followers"
        const val ARG_FOLLOWINGS = "followings"
    }
}