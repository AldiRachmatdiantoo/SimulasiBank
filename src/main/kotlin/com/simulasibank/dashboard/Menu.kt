package com.simulasibank.dashboard

import com.simulasibank.auth.User
import checkList
import java.sql.Connection

class Menu(val conn: Connection) {
    val listMenu = mutableListOf(
        "transfer",
        "tarik",
        "tambah rekening",
        "setor uang",
        "cek akun",
        "keluar"
    )
    fun menuBank(user: User){
        while (true) {
            val chooseMenu = checkList("Pilih: ", "menu", listMenu)
            val continueOrNot = organizeInputMenu(chooseMenu, user)
            if (!continueOrNot) return
        }
    }
    fun organizeInputMenu(chooseMenu: String, user: User): Boolean{
        when(chooseMenu){
            listMenu[0] -> OrganizeBank(conn, user).transfer()
            listMenu[1] -> OrganizeBank(conn, user).tarik()
            listMenu[2] -> OrganizeBank(conn, user).tambahRekening()
            listMenu[3] -> OrganizeBank(conn, user).setorUang()
            listMenu[4] -> Profile(user, conn).menuAkun()
            listMenu[5] -> return false
        }
        return true
    }
}