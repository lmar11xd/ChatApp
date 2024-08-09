package com.lmar.chatapp

import android.text.format.DateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Locale

object Constantes {

    const val MENSAJE_TIPO_TEXTO = "TEXTO"
    const val MENSAJE_TIPO_IMAGEN = "IMAGEN"

    const val NOTIFICACION_NUEVO_MENSAJE = "NOTIFICACION_NUEVO_MENSAJE"
    const val FCM_SERVER_KEY = "AAAABjAITWA:APA91bENKiriqbeQdHgBPrMgQc6MD1xqDxwv2HoxSrGG2pzSeJuvAMRJhmQOyk_Ip5ebnt-f0GxcYV9h3w7R4EmnHCG1zD5uDWwJGi7oh8F2iYcQTfDHNOGumGAOImyMTnyfa9127DUD"

    const val URL_API_MESSAGE = "https://fcm.googleapis.com/v1/projects/chatting-lmar/messages:send"

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