package com.lmar.chatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.lmar.chatapp.databinding.ActivityLoginEmailBinding

class LoginEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.tvRecuperarCuenta.setOnClickListener {
            startActivity(Intent(applicationContext, OlvideContrasenaActivity::class.java))
        }

        binding.btnIngresar.setOnClickListener {
            validarInformacion()
        }

        binding.tvRegistrarme.setOnClickListener {
            startActivity(Intent(applicationContext, RegistroEmailActivity::class.java))
        }
    }

    private fun validarInformacion() {
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        if(email.isEmpty()) {
            binding.etEmail.error = "Ingrese su correo"
            binding.etEmail.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Correo no válido"
            binding.etEmail.requestFocus()
        }
        else if(password.isEmpty()) {
            binding.etPassword.error = "Ingrese su contraseña"
            binding.etPassword.requestFocus()
        } else if(password.length < 6) {
            binding.etPassword.error = "Contraseña debe tener mínimo 6 caracteres"
            binding.etPassword.requestFocus()
        }
        else {
            loguearUsuario()
        }
    }

    private fun loguearUsuario() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se realizó el logo debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}