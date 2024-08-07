package com.lmar.chatapp

import android.text.format.DateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Locale

object Constantes {

    const val MENSAJE_TIPO_TEXTO = "TEXTO"
    const val MENSAJE_TIPO_IMAGEN = "IMAGEN"

    fun obtenerTiempo(): Long {
        return System.currentTimeMillis()
    }

    fun formatoFecha(tiempo: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy", calendar).toString()
    }

    fun obtenerFechaHora(tiempo: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy hh:mm:a", calendar).toString()
    }

    fun rutaChat(receptorUid: String, emisorUid: String): String {
        val arrayUid = arrayOf(receptorUid, emisorUid)
        Arrays.sort(arrayUid)
        return "${arrayUid[0]}_${arrayUid[1]}"
    }
}