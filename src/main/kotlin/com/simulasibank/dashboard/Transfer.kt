package com.simulasibank.dashboard

import com.simulasibank.auth.User
import checkNullOrBlank
import com.simulasibank.connection.DatabaseConnection
import handle
import java.time.LocalDate

class Transfer(val rekeningTujuan: Int, val user: User) {
    val db = DatabaseConnection(
        "jdbc:mysql://localhost:3306/db_bank",
        "root",
        ""
    )
    val conn = db.getConnection()
    fun inputHowMuch(){
        var mySaldo = user.saldo
        val insertMoney = checkNullOrBlank("masukkan nominal: ").toInt()
        try {
            if (mySaldo < insertMoney) throw Exception("Saldo Tidak cukup!")
            mySaldo -= insertMoney
            transferTransaksi(mySaldo)
            getTransfer(insertMoney)
            val date = LocalDate.now()
            val keteranganTransaksi = checkNullOrBlank("keterangan: ")
            val metodeTransaksi = checkNullOrBlank("Pembayaran memakai: ")
            OrganizeBank(conn, user).cetakMutasi(
                rekeningTujuan,
                date,
                metodeTransaksi,
                keteranganTransaksi,
                insertMoney,
                mySaldo
                )
            handle(OutputsOperation.Success("Transfer Berhasil!"))
        } catch (e: Exception){
            handle(OutputsOperation.Error("${e.message}"))
        }

    }
    fun transferTransaksi(mySaldo: Int){
        val stmt = conn.prepareStatement(
            "UPDATE users SET saldo = ? WHERE norekening = ?"
        )
        stmt.setInt(1, mySaldo)
        stmt.setInt(2, user.noRekening)
        stmt.executeUpdate()
    }
    fun getTransfer(insertMoney: Int){
        val stmt = conn.prepareStatement(
            "UPDATE users SET saldo = saldo + ? WHERE norekening = ?"
        )
        stmt.setInt(1, insertMoney)
        stmt.setInt(2, rekeningTujuan)
        stmt.executeUpdate()
    }
}