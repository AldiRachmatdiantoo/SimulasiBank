package com.simulasibank.dashboard

import com.simulasibank.auth.User
import java.sql.Connection
import checkList
import checkNullOrBlank
import handle

class OrganizeBank(val conn: Connection, val user: User) {
    val listWithRekening = mutableMapOf<String, Int>()




    fun transfer(){
        val stmt = conn.prepareStatement(
            "SELECT name, norekening FROM users"
        )
        val rs = stmt.executeQuery()
        while (rs.next()){
            val peopleName = rs.getString("name")
            val peopleNoRek = rs.getInt("norekening")
            if (peopleName != user.name){
                listWithRekening.put(peopleName, peopleNoRek)
            }
        }
        val input = checkList("Masukkan nama: ", "LIST REKENING", listWithRekening.keys.toMutableList())
        val outputFromInput = listWithRekening.getValue(input)

        val jumlahTransaksi = checkNullOrBlank("Masukkan jumlah Transaksi: ")
        val jumlahTransaksiToInt = jumlahTransaksi.toInt()

        try {
            if (user.saldo < jumlahTransaksiToInt) throw Exception("saldo tidak cukup!")
            user.saldo -= jumlahTransaksiToInt
        } catch (e: Exception){
            handle(OutputsOperation.Error("${e.message}"))
            print("continue..")
            readln()
            return
        }


        val stmtForSaldo = conn.prepareStatement(
            "UPDATE users SET saldo = ? WHERE norekening = ?"
        )
        stmtForSaldo.setInt(1, user.saldo)
        stmtForSaldo.setInt(2, user.noRekening)
        stmtForSaldo.executeUpdate()
        handle(OutputsOperation.Success("Berhasil mentransfer!"))
        return






    }
    fun tarik(){

    }
    fun tambahRekening(){

    }
    fun setorUang(){

    }

}