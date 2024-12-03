package com.capstone.diabticapp.helper

import java.text.SimpleDateFormat
import java.util.*

object DateConverterUtils {

    /**
     * Mengonversi waktu UTC ke format WIB dengan hari, tanggal, bulan, tahun, jam, dan menit.
     * @param utcTime String waktu dalam format UTC (contoh: "2024-12-01T08:39:01.506Z").
     * @return String waktu dalam format WIB (contoh: "Minggu, 01-Desember-2024 15:39").
     */
    fun convertUtcToWib(utcTime: String): String {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = utcFormat.parse(utcTime)

        val wibFormat = SimpleDateFormat("EEEE, dd-MMMM-yyyy HH:mm", Locale("id", "ID"))
        wibFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        return wibFormat.format(date!!)
    }
}