package com.lmar.chatapp

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

object Constantes {

    fun obtenerTiempo(): Long {
        return System.currentTimeMillis()
    }

    fun formatoFecha(tiempo: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy", calendar).toString()
    }
}