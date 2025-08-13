package com.simulasibank.auth

import com.simulasibank.connection.DatabaseConnection
import checkNullOrBlank
import checkSqlTable
import handle
import remainingChance

class Login {
    val db = DatabaseConnection(
        "jdbc:mysql://localhost:3306/db_bank",
        "root",
        ""
    )
    val conn = db.getConnection()

    fun loginAcc(){
        while (true) {
            val username = checkUsername()
            val password = checkPassword(username)
            println("Berhasil")
            break
        }
    }
    fun checkUsername(): String{
        while (true) {
            val stmtSelectUsername = conn.prepareStatement("SELECT * FROM users WHERE name = ?")
            val username = checkNullOrBlank("masukkan username: ")
            stmtSelectUsername.setString(1, username)
            val checkProcess = checkSqlTable(stmtSelectUsername, username, "name")
            if (checkProcess) return username

            handle(OutputsOperation.Error("Username tidak ditemukan!"))
            continue
        }
    }
    fun checkPassword(username: String): String {
        while (true){
            val stmtSelectPassword = conn.prepareStatement("SELECT password FROM users WHERE name = ?")
            val password = checkNullOrBlank("masukkan password: ")
            stmtSelectPassword.setString(1, username)
            val checkProcess = checkSqlTable(stmtSelectPassword, password, "password")
            if (checkProcess) return password
            val remainingChance = remainingChance()
            handle(OutputsOperation.Error("password salah! sisa kesempatan: $remainingChance"))

        continue
    }
    }
}