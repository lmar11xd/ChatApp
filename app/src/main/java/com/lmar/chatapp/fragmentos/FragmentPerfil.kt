package com.lmar.chatapp.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lmar.chatapp.Constantes
import com.lmar.chatapp.EditarInformacionActivity
import com.lmar.chatapp.OpcionesLoginActivity
import com.lmar.chatapp.R
import com.lmar.chatapp.databinding.FragmentPerfilBinding

class FragmentPerfil : Fragment() {
    private lateinit var binding: FragmentPerfilBinding
    private lateinit var mContext: Context
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        cargarInformacion()

        binding.btnActualizarInfo.setOnClickListener {
            startActivity(Intent(mContext, EditarInformacionActivity::class.java))
        }

        binding.btnCerrarSesion.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(mContext, OpcionesLoginActivity::class.java))
            activity?.finishAffinity()
        }
    }

    private fun cargarInformacion() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child("${firebaseAuth.uid}")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nombres = "${snapshot.child("nombres").value}"
                    val email = "${snapshot.child("email").value}"
                    val proveedor = "${snapshot.child("proveedor").value}"
                    var tiempoReg = "${snapshot.child("tiempoReg").value}"
                    val imagen = "${snapshot.child("imagen").value}"

                    if(tiempoReg == "null") {
                        tiempoReg = "0"
                    }

                    //Conversion a fecha
                    val fecha = Constantes.formatoFecha(tiempoReg.toLong())

                    //Setear la información en las vistas
                    binding.tvNombres.text = nombres
                    binding.tvEmail.text = email
                    binding.tvProveedor.text = proveedor
                    binding.tvTiempoRegistro.text = fecha

                    //Setear la imagen
                    try {
                        Glide.with(mContext)
                            .load(imagen).placeholder(R.drawable.ic_img_perfil)
                            .into(binding.ivPerfil)
                    } catch (e: Exception) {
                        Toast.makeText(
                            mContext,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}