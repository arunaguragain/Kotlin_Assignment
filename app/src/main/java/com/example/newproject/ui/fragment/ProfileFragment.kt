package com.example.newproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newproject.R
import com.example.newproject.databinding.FragmentProfileBinding
import com.example.newproject.repository.UserRepositoryImpl
import com.example.newproject.ui.activity.EditProfileActivity
import com.example.newproject.viewmodel.UserViewModel
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // this vanera context lidaina requireContext() use garna parxa

        var  repo = UserRepositoryImpl()
        userViewModel =UserViewModel(repo)

        var  currentUser = userViewModel.getCurrentUser()

        currentUser.let {
            userViewModel.getUserFromDatabase(it?.uid.toString())
        }

        userViewModel.userData.observe(requireActivity()){users-> //can use it in place of variable"users"
            binding.profileEmail.setText(users?.email)
            binding.profileName.text = users?.firstName + " " +users?.lastName

            val profileImageUrl = users?.profileImageUrl
            if (!profileImageUrl.isNullOrEmpty()) {
                val updatedUrl = "$profileImageUrl?time=${System.currentTimeMillis()}"
                Picasso.get()
                    .load(updatedUrl)
                    .placeholder(R.drawable.baseline_person_24)
                    .into(binding.profileImage)
            } else {
                binding.profileImage.setImageResource(R.drawable.baseline_person_24)
            }
        }

        binding.cardEditProfile.setOnClickListener({
            val  intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        })
    }
}