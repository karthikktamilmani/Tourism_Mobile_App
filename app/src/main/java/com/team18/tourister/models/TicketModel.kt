package com.team18.tourister.models

data class TicketModel(
    var name: String,
    var email: String,
    var date: String,
    var price: Int,
    var from: String,
    var to: String,
    var count: Int,
    var payment_info: PaymentInfo

)