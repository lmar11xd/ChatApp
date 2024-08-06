package com.lmar.chatapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.lmar.chatapp.databinding.ActivityMainBinding
import com.lmar.chatapp.fragmentos.FragmentChats
import com.lmar.chatapp.fragmentos.FragmentPerfil
import com.lmar.chatapp.fragmentos.FragmentUsuarios

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser == null) {
            irOpcionesLogin()
        }

        //Fragmento por defecto
        verFragmentoPerfil()

        binding.bottomNV.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.item_perfil -> {
                    verFragmentoPerfil()
                    true
                }
                R.id.item_usuarios -> {
                    verFragmentoUsuarios()
                    true
                }
                R.id.item_chats -> {
                    verFragmentoChats()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun irOpcionesLogin() {
        startActivity(Intent(applicationContext, OpcionesLoginActivity::class.java))
        finishAffinity()
    }

    private fun verFragmentoPerfil() {
        binding.tvTitulo.text = "Perfil"

        val fragment = FragmentPerfil()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flFragmento.id, fragment, "Fragment Perfil")
        fragmentTransaction.commit()
    }

    private fun verFragmentoUsuarios() {
        binding.tvTitulo.text = "Usuarios"

        val fragment = FragmentUsuarios()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flFragmento.id, fragment, "Fragment Usuarios")
        fragmentTransaction.commit()
    }

    private fun verFragmentoChats() {
        binding.tvTitulo.text = "Chats"

        val fragment = FragmentChats()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flFragmento.id, fragment, "Fragment Chats")
        fragmentTransaction.commit()
    }
}