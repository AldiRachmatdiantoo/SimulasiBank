package com.simulasibank.dashboard

import com.simulasibank.auth.User
import java.sql.Connection
import handleMapList
import java.sql.Date
import java.time.LocalDate

class OrganizeBank(val conn: Connection, val user: User) {

    fun transfer(){
        val listRekening = Profile(user, conn).listRekening()
        val inputName = handleMapList("Pilih Rekening: ", listRekening)
        if (inputName != "-") {
            val convertToValue = listRekening.getValue(inputName)
            Transfer(convertToValue, user).inputHowMuch()
        }
        return
    }
    fun tarik(){}
    fun tambahRekening(){}
    fun setorUang(){}

    //no rekening, tanggal Transaksi, metode transaksi, keterangan transaksi, jumlah transaksi, sisa saldo
    fun cetakMutasi(
        noRekening: Int,
        tanggalTransaksi: LocalDate,
        metodeTransaksi: String,
        keteranganTransaksi: String,
        jumlahTransaksi: Int,
        sisaSaldo: Int
    ){
        val stmt = conn.prepareStatement(
            "INSERT INTO wallet (noRekening, tanggalTransaksi, metodeTransaksi, keteranganTransaksi, jumlahTransaksi, sisaSaldo) VALUES (?,?,?,?,?,?)"
        )
        stmt.setInt(1, noRekening)
        stmt.setDate(2, Date.valueOf(tanggalTransaksi))
        stmt.setString(3, metodeTransaksi)
        stmt.setString(4, keteranganTransaksi)
        stmt.setInt(5, jumlahTransaksi)
        stmt.setInt(6, sisaSaldo)

        stmt.executeUpdate()
        Mutasi(noRekening, tanggalTransaksi, metodeTransaksi, keteranganTransaksi, jumlahTransaksi, sisaSaldo)
    }

}