package com.lmar.chatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.lmar.chatapp.databinding.ActivityCambiarContrasenaBinding

class CambiarContrasenaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCambiarContrasenaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.ibRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnCambiarContrasena.setOnClickListener {
            validarInformacion()
        }
    }

    private var contrasena_actual = ""
    private var contrasena_nueva = ""
    private var repetir_contrasena = ""
    private fun validarInformacion() {
        contrasena_actual = binding.etContrasenaActual.text.toString().trim()
        contrasena_nueva = binding.etContrasenaNueva.text.toString().trim()
        repetir_contrasena = binding.etRepetirContrasena.text.toString().trim()

        if(contrasena_actual.isEmpty()) {
            binding.etContrasenaActual.error = "Ingrese contraseña actual"
            binding.etContrasenaActual.requestFocus()
        }
        else if(contrasena_nueva.isEmpty()) {
            binding.etContrasenaNueva.error = "Ingrese contraseña nueva"
            binding.etContrasenaNueva.requestFocus()
        }
        else if(repetir_contrasena.isEmpty()) {
            binding.etRepetirContrasena.error = "Repita contraseña nueva"
            binding.etRepetirContrasena.requestFocus()
        }
        else if(contrasena_nueva != repetir_contrasena) {
            binding.etRepetirContrasena.error = "No coinciden las contraseñas"
            binding.etRepetirContrasena.requestFocus()
        }
        else {
            autenticarUsuario()
        }
    }

    private fun autenticarUsuario() {
        progressDialog.setMessage("Autenticando usuario")
        progressDialog.show()

        val authCredential = EmailAuthProvider
            .getCredential(firebaseUser.email.toString(), contrasena_actual)
        firebaseUser.reauthenticate(authCredential)
            .addOnSuccessListener {
                progressDialog.dismiss()
                actualizarContrasena()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Falló la autenticación debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarContrasena() {
        progressDialog.setMessage("Cambiando contraseña")
        progressDialog.show()

        firebaseUser.updatePassword(contrasena_nueva)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "La contraseña se ha actualizado", Toast.LENGTH_SHORT).show()
                cerrarSesion()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Falló la autenticación debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cerrarSesion() {
        firebaseAuth.signOut()
        startActivity(Intent(applicationContext, OpcionesLoginActivity::class.java))
        finishAffinity()
    }
}