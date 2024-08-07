package com.lmar.chatapp.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.lmar.chatapp.Constantes
import com.lmar.chatapp.R
import com.lmar.chatapp.entidad.Chat

class AdaptadorChat: RecyclerView.Adapter<AdaptadorChat.HolderChat> {

    private val context: Context
    private val chatArray: ArrayList<Chat>
    private val firebaseAuth: FirebaseAuth

    companion object {
        private const val MENSAJE_IZQUIERDO = 0
        private const val MENSAJE_DERECHO = 1
    }
    constructor(context: Context, chatArray: ArrayList<Chat>) {
        this.context = context
        this.chatArray = chatArray
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderChat {
        if(viewType == MENSAJE_DERECHO) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false)
            return HolderChat(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false)
            return HolderChat(view)
        }
    }

    override fun getItemCount(): Int {
        return chatArray.size
    }

    override fun onBindViewHolder(holder: HolderChat, position: Int) {
        val chat = chatArray[position]

        val mensaje = chat.mensaje
        val tipoMensaje = chat.tipoMensaje
        val tiempo = chat.tiempo

        val formato = Constantes.obtenerFechaHora(tiempo)
        holder.tvTiempo.text = formato

        if (tipoMensaje == Constantes.MENSAJE_TIPO_TEXTO) {
            holder.tvMensaje.visibility = View.VISIBLE
            holder.ivMensaje.visibility = View.GONE

            holder.tvMensaje.text = mensaje
        } else {
            holder.tvMensaje.visibility = View.GONE
            holder.ivMensaje.visibility = View.VISIBLE

            try {
                Glide.with(context)
                    .load(mensaje)
                    .placeholder(R.drawable.imagen_enviada)
                    .error(R.drawable.image_not_found)
                    .into(holder.ivMensaje)
            } catch (e: Exception){

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(chatArray[position].emisorUid == firebaseAuth.uid) {
            return MENSAJE_DERECHO
        } else {
            return MENSAJE_IZQUIERDO
        }
    }

    inner class HolderChat(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvMensaje: TextView = itemView.findViewById(R.id.tv_mensaje)
        var ivMensaje: ShapeableImageView = itemView.findViewById(R.id.iv_mensaje)
        var tvTiempo: TextView = itemView.findViewById(R.id.tv_mensaje_tiempo)

    }

}