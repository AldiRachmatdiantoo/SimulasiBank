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
            listMenuAkun[2] -> return
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
    fun saldo(){
        println("\n=====SALDO=====")
        println("Saldo anda: ${user.saldo}")

    }
    fun listRekening(){
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
            }
        }
        userListRek.forEachIndexed { index, value ->
            println("${index+1}. Nama: ${value.name} | No.Rekening: ${value.noRekening}")
        }
    }

}