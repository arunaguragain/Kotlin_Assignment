package com.example.newproject.model

data class TableBookingModel(
    var bookingId: String =" ",
    var customerName: String =" ",
    var date: String =" ",
    var time: String = " ",
    var guests : Int = 0,
    var specialRequest: String? = null
) {
}

