package com.example.newproject.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newproject.R
import com.example.newproject.databinding.ActivityLoginBinding
import com.example.newproject.databinding.ActivityTableBookingBinding
import com.example.newproject.model.TableBookingModel
import com.example.newproject.viewmodel.TableBookingViewModel
import com.example.newproject.viewmodel.UserViewModel

class TableBookingActivity : AppCompatActivity() {
    lateinit var binding: ActivityTableBookingBinding
    lateinit var tableBookingViewModel: TableBookingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTableBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tableBookingViewModel = ViewModelProvider(this).get(TableBookingViewModel::class.java)

        tableBookingViewModel.bookingStatus.observe(this, Observer { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        })

        binding.btnBookTable.setOnClickListener {
            val customerName = binding.etName.text.toString()
            val date = binding.etDate.text.toString()
            val time = binding.etTime.text.toString()
            val guestCount = binding.etGuests.text.toString().toIntOrNull()

            // Validate input fields
            if (customerName.isEmpty() || date.isEmpty() || time.isEmpty() || guestCount == null || guestCount <= 0) {
                Toast.makeText(this, "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val booking = TableBookingModel(
                bookingId = "", // Assuming new booking, so bookingId is empty
                customerName = customerName,
                date = date,
                time = time,
                guests = guestCount
            )

            // Call ViewModel to create booking
            tableBookingViewModel.createBooking(booking)
        }


//        binding.btnUpdateTable.setOnClickListener {
//            val booking = TableBookingModel(
//                bookingId = "existingBookingId", // Set an existing booking ID
//                customerName = binding.etName.text.toString(),
//                date = binding.etDate.text.toString(),
//                time = binding.etTime.text.toString(),
//                guests = binding.etGuests.text.toString().toIntOrNull() ?: 1
//            )
//            viewModel.updateBooking(booking)
//        }
//
//        // Delete booking
//        binding.btnDeleteTable.setOnClickListener {
//            viewModel.deleteBooking("existingBookingId") // Provide the booking ID to delete
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}