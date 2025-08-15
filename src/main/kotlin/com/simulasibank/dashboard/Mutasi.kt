package com.simulasibank.dashboard

import java.time.LocalDate

data class Mutasi(
    val noRekening: Int,
    val tanggalTransaksi: LocalDate,
    val metodeTransaksi: String,
    val keteranganTransaksi: String,
    val jumlahTransaksi: Int,
    val sisaSaldo: Int
)
