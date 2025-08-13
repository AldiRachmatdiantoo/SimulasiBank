package com.simulasibank.dashboard

import com.simulasibank.auth.User
import java.sql.Connection

class Menu(connection: Connection) {
    val conn = connection
    fun menuBank(user: User){
        println(user)
    }
}