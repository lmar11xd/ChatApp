package com.lmar.chatapp.entidad

class Chat {
    var idMensaje: String = ""
    var tipoMensaje: String = ""
    var mensaje: String = ""
    var emisorUid: String = ""
    var receptorUid: String = ""
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
        this.receptorUid = receptopUid
        this.tiempo = tiempo
    }

}