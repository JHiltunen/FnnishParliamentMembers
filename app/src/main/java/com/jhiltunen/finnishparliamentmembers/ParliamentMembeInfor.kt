package com.jhiltunen.finnishparliamentmembers

data class ParliamentMemberInfo(
    val hetekaId: Int,
    val seatNumber: Int = 0,
    val lastname: String,
    val firstname: String,
    val party: String,
    val minister: Boolean = false,
    val pictureUrl: String = ""
)