package com.simulasibank.connection

import java.sql.Connection
import java.sql.DriverManager

data class DatabaseConnection(
    val url: String,
    val root: String,
    val password: String
){
    fun getConnection() : Connection{
        return DriverManager.getConnection(url, root, password)
    }
}
