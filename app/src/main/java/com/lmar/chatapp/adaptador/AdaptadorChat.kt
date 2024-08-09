package com.lmar.chatapp.adaptador

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.lmar.chatapp.Constantes
import com.lmar.chatapp.R
import com.lmar.chatapp.entidad.Chat

class AdaptadorChat: RecyclerView.Adapter<AdaptadorChat.HolderChat> {

    private val context: Context
    private val chatArray: ArrayList<Chat>
    private val firebaseAuth: FirebaseAuth
    private var chatRuta = ""

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

            if(chat.emisorUid == firebaseAuth.uid){
                holder.itemView.setOnClickListener {
                    val opciones = arrayOf<CharSequence>("Eliminar mensaje", "Cancelar")
                    val builder: AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("¿Qué desea realizar?")
                    builder.setItems(opciones, DialogInterface.OnClickListener{ dialog, which ->
                        if(which == 0) {
                            eliminarMensaje(position, holder, chat)
                        }
                    })
                    builder.show()
                }
            }
        }
        else {
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

            if(chat.emisorUid == firebaseAuth.uid) {
                holder.itemView.setOnClickListener {
                    val opciones = arrayOf<CharSequence>("Eliminar imagen", "Ver imagen", "Cancelar")
                    val builder: AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("¿Qué desea realizar?")
                    builder.setItems(opciones, DialogInterface.OnClickListener{ dialog, which ->
                        if(which == 0) {
                            eliminarMensaje(position, holder, chat)
                        } else if(which == 1) {
                            vizualizarImagen(chat.mensaje)
                        }
                    })
                    builder.show()
                }
            } else {
                holder.itemView.setOnClickListener {
                    val opciones = arrayOf<CharSequence>("Ver imagen", "Cancelar")
                    val builder: AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("¿Qué desea realizar?")
                    builder.setItems(opciones, DialogInterface.OnClickListener{ dialog, which ->
                        if(which == 0) {
                            vizualizarImagen(chat.mensaje)
                        }
                    })
                    builder.show()
                }
            }
        }
    }

    private fun eliminarMensaje(position: Int, holder: AdaptadorChat.HolderChat, chat: Chat) {
        chatRuta = Constantes.rutaChat(chat.receptorUid, chat.emisorUid)
        val ref = FirebaseDatabase.getInstance().reference.child("Chats")
        ref.child(chatRuta).child(chatArray[position].idMensaje)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(holder.itemView.context, "Se ha eliminado el mensaje", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(holder.itemView.context, "No se ha eliminado el mensaje debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemViewType(position: Int): Int {
        if(chatArray[position].emisorUid == firebaseAuth.uid) {
            return MENSAJE_DERECHO
        } else {
            return MENSAJE_IZQUIERDO
        }
    }

    private fun vizualizarImagen(imagen: String) {
        val photoView: PhotoView
        val btnCerrar: MaterialButton
        val dialog = Dialog(context)

        dialog.setContentView(R.layout.vizualizador_imagenes)

        photoView = dialog.findViewById(R.id.pv_imagen)
        btnCerrar = dialog.findViewById(R.id.btn_cerrar)

        try {
            Glide.with(context)
                .load(imagen)
                .placeholder(R.drawable.imagen_enviada)
                .into(photoView)
        } catch (e: Exception) {

        }

        btnCerrar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
    }

    inner class HolderChat(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvMensaje: TextView = itemView.findViewById(R.id.tv_mensaje)
        var ivMensaje: ShapeableImageView = itemView.findViewById(R.id.iv_mensaje)
        var tvTiempo: TextView = itemView.findViewById(R.id.tv_mensaje_tiempo)

    }

}