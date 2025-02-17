package com.example.newproject.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityUpdateBookingBinding
import com.example.newproject.model.TableBookingModel
import com.example.newproject.viewmodel.TableBookingViewModel

class UpdateBookingActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBookingBinding
    lateinit var  tableBookingViewModel: TableBookingViewModel
    lateinit var booking: TableBookingModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        booking = intent.getParcelableExtra("booking")!!

        binding.etCustomerName.setText(booking.customerName)
        binding.etDate.setText(booking.date)
        binding.etTime.setText(booking.time)
        binding.etGuests.setText(booking.guests.toString())

        binding.btnUpdateBooking.setOnClickListener {
            booking.customerName = binding.etCustomerName.text.toString()
            booking.date = binding.etDate.text.toString()
            booking.time = binding.etTime.text.toString()
            booking.guests = binding.etGuests.text.toString().toInt()

            tableBookingViewModel.updateBooking(booking)
            Toast.makeText(this, "Booking updated!", Toast.LENGTH_SHORT).show()
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}










