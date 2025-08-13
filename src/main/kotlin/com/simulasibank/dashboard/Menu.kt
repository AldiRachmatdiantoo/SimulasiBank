package com.simulasibank.dashboard

import com.simulasibank.auth.User
import java.sql.Connection
import checkList

class Menu(connection: Connection) {
    val listMenu = mutableListOf(
        "transfer",
        "tarik",
        "tambah rekening",
        "setor uang",
        "cek akun",
        "keluar"
    )
    val conn = connection
    fun menuBank(user: User){
        while (true) {
            val chooseMenu = checkList("Pilih: ", "menu", listMenu)
            val continueOrNot = organizeInputMenu(chooseMenu)
            if (!continueOrNot) return
        }
    }
    fun organizeInputMenu(chooseMenu: String): Boolean{
        when(chooseMenu){
            listMenu[0] -> OrganizeBank().transfer()
            listMenu[1] -> OrganizeBank().tarik()
            listMenu[2] -> OrganizeBank().tambahRekening()
            listMenu[3] -> OrganizeBank().setorUang()
            listMenu[4] -> Profile().menuAkun()
            listMenu[5] -> return false
        }
        return true
    }
}