package com.simulasibank.dashboard

import checkList
class Profile {
    val listMenuAkun = mutableListOf(
        "informasi rekening",
        "saldo",
        "mutasi rekening",
        "tagihan",
        "list rekening",
        "kembali"
    )
    fun menuAkun(){
        val chooseMenu = checkList("Pilih: ", "akun saya", listMenuAkun)
        return
    }

}