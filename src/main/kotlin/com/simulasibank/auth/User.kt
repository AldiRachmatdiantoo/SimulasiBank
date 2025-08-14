package com.simulasibank.auth

data class User(
    val name: String,
    val email: String,
    val noRekening: Int,
    var saldo: Int
)
