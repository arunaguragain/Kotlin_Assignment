package com.example.newproject.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityEditProfile2Binding
import com.example.newproject.repository.UserRepositoryImpl
import com.example.newproject.viewmodel.UserViewModel

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfile2Binding
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditProfile2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        val currentUser = userViewModel.getCurrentUser()
        if (currentUser != null) {
            userViewModel.getUserFromDatabase(currentUser.uid)
        }

        userViewModel.userData.observe(this) {
            binding.UpdateFname.setText(it?.firstName.toString())
            binding.UpdateLname.setText(it?.lastName.toString())
            binding.UpdateAddress.setText(it?.address.toString())
            binding.UpdateContact.setText(it?.phoneNumber.toString())
            binding.updateEmail.setText(it?.email.toString())
        }

        binding.btnUpdateProfile.setOnClickListener{
            val  firstName = binding.UpdateFname.text.toString()
            val lastName = binding.UpdateLname.text.toString()
            val address = binding.UpdateAddress.text.toString()
            val contact = binding.UpdateContact.text.toString()
            val email = binding.updateEmail.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var updatedMap = mutableMapOf<String,Any>()

            updatedMap["firstName"] = firstName
            updatedMap["lastName"] = lastName
            updatedMap["address"] = address
            updatedMap["contact"] = contact
            updatedMap["email"] = email

            val userId = currentUser?.uid
            if (userId != null) {
                userViewModel.editProfile(userId, updatedMap) { success, message ->
                    if (success) {
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Update failed: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            }

        }

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}