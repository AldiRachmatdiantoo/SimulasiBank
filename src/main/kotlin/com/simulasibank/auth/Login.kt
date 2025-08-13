package com.simulasibank.auth

import com.simulasibank.connection.DatabaseConnection
import checkNullOrBlank
import checkSqlTable
import checkGetSql
import handle
import remainingChance
import com.simulasibank.dashboard.Menu

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
            val email = checkEmail(username)
            val saldo = checkSaldo(username)
            val noRekening = checkNoRek(username)
            if (checkPassword(username)) {
                handle(OutputsOperation.Success("Berhasil Login!"))
                val user = User(username, email, noRekening, saldo)
                Menu(conn).menuBank(user)
            }

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
    fun checkPassword(username: String): Boolean {
        while (true){
            val stmtSelectPassword = conn.prepareStatement("SELECT password FROM users WHERE name = ?")
            val password = checkNullOrBlank("masukkan password: ")
            stmtSelectPassword.setString(1, username)
            val checkProcess = checkSqlTable(stmtSelectPassword, password, "password")
            if (checkProcess) return true
            val remainingChance = remainingChance()
            handle(OutputsOperation.Error("password salah! sisa kesempatan: $remainingChance"))

        continue
    }
    }
    fun checkEmail(username: String): String{
        val email = conn.prepareStatement("SELECT email FROM users WHERE name = ?")
        email.setString(1, username)
        val myEmail = checkGetSql(email, "email")
        if (myEmail != "-") return myEmail
        return "-"
    }
    fun checkNoRek(username: String): Int{
        val noRekening = conn.prepareStatement("SELECT norekening FROM users WHERE name = ? ")
        noRekening.setString(1, username)
        val noRek = checkGetSql(noRekening, "norekening")

        if (noRek != "-") return noRek.toInt()
        return 0
    }
    fun checkSaldo(username: String): Int{
        val saldo = conn.prepareStatement("SELECT saldo FROM users WHERE name = ?")
        saldo.setString(1, username)
        val cekSaldo = checkGetSql(saldo, "saldo")
        if (cekSaldo != "") return cekSaldo.toInt()
        return 0
    }
}