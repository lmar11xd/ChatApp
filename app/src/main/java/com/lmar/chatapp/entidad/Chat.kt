package com.lmar.chatapp.entidad

class Chat {
    var idMensaje: String = ""
    var tipoMensaje: String = ""
    var mensaje: String = ""
    var emisorUid: String = ""
    var receptopUid: String = ""
    var tiempo: Long = 0

    constructor()
    constructor(
        idMensaje: String,
        tipoMensaje: String,
        mensaje: String,
        emisorUid: String,
        receptopUid: String,
        tiempo: Long
    ) {
        this.idMensaje = idMensaje
        this.tipoMensaje = tipoMensaje
        this.mensaje = mensaje
        this.emisorUid = emisorUid
        this.receptopUid = receptopUid
        this.tiempo = tiempo
    }

}