package com.simulasibank.dashboard

import checkList
import com.simulasibank.auth.User
import java.sql.Connection

class Profile(val user: User, val conn: Connection) {
    val listMenuAkun = mutableListOf(
        "informasi rekening",
        "saldo",
        "mutasi rekening",
        "tagihan",
        "list rekening",
        "kembali"
    )
    val userListRek = mutableListOf<User>()
    val mutasiList = mutableListOf<Mutasi>()
    val userListRekMap = mutableMapOf<String, Int>()
    fun menuAkun(){
        while (true) {
            val chooseMenu = checkList("Pilih: ", "akun saya", listMenuAkun)
            if (chooseMenu == listMenuAkun[5]) return
            menuTo(chooseMenu)

        }
    }
    fun menuTo(chooseMenu: String){
        when(chooseMenu){
            listMenuAkun[0] -> {
                informasiRekening()
                print("press any key to continue..")
                readln()
                return
            }
            listMenuAkun[1] -> {
                saldo()
                print("press any key to continue..")
                readln()
                return
            }
            listMenuAkun[2] -> mutasiRekening()
//            listMenuAkun[3] -> tagihan()
            listMenuAkun[4] -> {
                listRekening()
                print("Press any key to continue..")
                readln()
            }
            listMenuAkun[5] -> return
        }
    }
    fun informasiRekening(){
        println("Nama Pemilik: ${user.name}\nNo.Rekening: ${user.noRekening}\nEmail: ${user.email}\n")

    }
    fun saldo(): Int{
        println("\n=====SALDO=====")
        val stmt = conn.prepareStatement(
            "SELECT saldo FROM users WHERE norekening = ?"
        )
        stmt.setInt(1, user.noRekening)
        val rs = stmt.executeQuery()
        if (rs.next()){
            val saldoNow = rs.getInt("saldo")
            println("Saldo Anda: Rp.$saldoNow")
            return saldoNow
        }
        return 0
    }
    fun listRekening(): MutableMap<String, Int>{
        val stmtCheckListRekening = conn.prepareStatement(
            "SELECT * FROM users LEFT JOIN wallet ON users.norekening = wallet.noRekening"
        )
        val rs = stmtCheckListRekening.executeQuery()
        while (rs.next()){
            val name =  rs.getString("name")
            val noRek = rs.getInt("norekening")
            val saldo = rs.getInt("saldo")
            val email = rs.getString("email")
            if (name != user.name) {
                val toWhere = User(name,email,noRek,saldo)
                userListRek.add(toWhere)
                userListRekMap.put(toWhere.name, toWhere.noRekening)
            }
        }
        userListRek.forEachIndexed { index, value ->
            println("${index+1}. Nama: ${value.name} | No.Rekening: ${value.noRekening}")
        }
        return userListRekMap
    }
    fun mutasiRekening(){
        println("\n=====MUTASI REKENING=====")
       val stmt = conn.prepareStatement(
           "SELECT * FROM wallet WHERE noRekening = ?"
       )
        stmt.setInt(1,user.noRekening)
        val rs = stmt.executeQuery()
        while (rs.next()){
            val noRekening = rs.getInt("noRekening")
            val tglTransaksi = rs.getDate("tanggalTransaksi")
            val metode = rs.getString("metodeTransaksi")
            val keterangan = rs.getString("keteranganTransaksi")
            val jumlah = rs.getInt("jumlahTransaksi")
            val sisa = rs.getInt("sisaSaldo")
            val tglTransaksiLocal = tglTransaksi.toLocalDate()

            val toWhere = Mutasi(noRekening, tglTransaksiLocal,metode,keterangan,jumlah,sisa)
            mutasiList.add(toWhere)

        }
        mutasiList.forEachIndexed { index, value ->
            println("${index+1}.$value")
        }

    }

}