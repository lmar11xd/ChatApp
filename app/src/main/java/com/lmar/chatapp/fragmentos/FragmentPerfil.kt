package com.lmar.chatapp.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import com.lmar.chatapp.CambiarContrasenaActivity
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

        binding.btnCambiarContrasena.visibility = View.GONE

        firebaseAuth = FirebaseAuth.getInstance()

        cargarInformacion()

        binding.btnActualizarInfo.setOnClickListener {
            startActivity(Intent(mContext, EditarInformacionActivity::class.java))
        }

        binding.btnCambiarContrasena.setOnClickListener {
            startActivity(Intent(mContext, CambiarContrasenaActivity::class.java))
        }

        binding.btnCerrarSesion.setOnClickListener{
            actualizarEstado()
            cerrarSesion()
        }
    }

    private fun cerrarSesion(){
        object: CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                //Pasado 2 segundos se va ejecutar esta línea de código
                firebaseAuth.signOut()
                startActivity(Intent(mContext, OpcionesLoginActivity::class.java))
                activity?.finishAffinity()
            }
        }.start()
    }

    private fun actualizarEstado() {
        val ref = FirebaseDatabase.getInstance().reference.child("Usuarios").child(firebaseAuth.uid!!)

        val hashMap = HashMap<String, Any>()
        hashMap["estado"] = "Offline"
        ref!!.updateChildren(hashMap)
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
                        Glide.with(mContext.applicationContext)
                            .load(imagen).placeholder(R.drawable.ic_img_perfil)
                            .into(binding.ivPerfil)
                    } catch (e: Exception) {
                        Toast.makeText(
                            mContext,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    if(proveedor == "Email") {
                        binding.btnCambiarContrasena.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}