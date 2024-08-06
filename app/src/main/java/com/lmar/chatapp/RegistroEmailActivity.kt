package com.lmar.chatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.lmar.chatapp.databinding.ActivityRegistroEmailBinding

class RegistroEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var nombres = ""
    private var email = ""
    private var password = ""
    private var repPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrar.setOnClickListener{
            validarInformacion()
        }
    }

    private fun validarInformacion() {
        nombres = binding.etNombres.text.toString().trim()
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        repPassword = binding.etRepPassword.text.toString().trim()

        if(nombres.isEmpty()) {
            binding.etNombres.error = "Ingrese su nombre"
            binding.etNombres.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Correo no válido"
            binding.etEmail.requestFocus()
        }
        else if(email.isEmpty()) {
            binding.etEmail.error = "Ingrese su correo"
            binding.etEmail.requestFocus()
        }
        else if(password.isEmpty()) {
            binding.etPassword.error = "Ingrese su contraseña"
            binding.etPassword.requestFocus()
        }
        else if(password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            binding.etPassword.requestFocus()
        }
        else if(repPassword.isEmpty()) {
            binding.etRepPassword.error = "Repita su contraseña"
            binding.etRepPassword.requestFocus()
        }
        else if(password != repPassword) {
            binding.etRepPassword.error = "Las contraseñas no coinciden"
            binding.etRepPassword.requestFocus()
        }
        else  {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { actualizarInformacion() }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Falló la creación de la cuenta debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun actualizarInformacion() {
        progressDialog.setMessage("Guardando información")

        val uidU = firebaseAuth.uid
        val nombreU = nombres
        val emailU = firebaseAuth.currentUser!!.email
        val tiempoRegistro = Constantes.obtenerTiempo()

        val datosUsuario = HashMap<String, Any>()
        datosUsuario["uid"] = "$uidU"
        datosUsuario["nombres"] = "$nombreU"
        datosUsuario["email"] = "$emailU"
        datosUsuario["tiempoReg"] = "$tiempoRegistro"
        datosUsuario["proveedor"] = "Email"
        datosUsuario["estado"] = "Online"
        datosUsuario["imagen"] = ""

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidU!!)
            .setValue(datosUsuario)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finishAffinity()//Destruir activity
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Falló la creación de la cuenta debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}