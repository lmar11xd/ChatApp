package com.lmar.chatapp.fragmentos

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lmar.chatapp.adaptador.AdaptadorUsuario
import com.lmar.chatapp.databinding.FragmentUsuariosBinding
import com.lmar.chatapp.entidad.Usuario

class FragmentUsuarios : Fragment() {

    private lateinit var binding: FragmentUsuariosBinding
    private lateinit var mContext: Context

    private var adaptadorUsuario: AdaptadorUsuario? = null
    private var listaUsuarios: List<Usuario>? = null

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsuariosBinding.inflate(layoutInflater, container, false)

        binding.rvUsuarios.setHasFixedSize(true)
        binding.rvUsuarios.layoutManager = LinearLayoutManager(mContext)

        listaUsuarios = ArrayList()

        binding.etBuscarUsuario.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buscarUsuario(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        listarUsuarios()

        return binding.root
    }

    private fun listarUsuarios() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        val reference = FirebaseDatabase.getInstance().reference.child("Usuarios").orderByChild("nombres")
        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (listaUsuarios as ArrayList<Usuario>).clear()
                if(binding.etBuscarUsuario.text.toString().isEmpty()) {
                    for (sn in snapshot.children) {
                        val usuario: Usuario? = sn.getValue(Usuario::class.java)
                        if(!(usuario!!.uid).equals(firebaseUser)) {
                            (listaUsuarios as ArrayList<Usuario>).add(usuario)
                        }
                    }

                    adaptadorUsuario = AdaptadorUsuario(mContext, listaUsuarios!!)
                    binding.rvUsuarios.adapter = adaptadorUsuario
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun buscarUsuario(buscar: String) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        val reference = FirebaseDatabase.getInstance().reference.child("Usuarios").orderByChild("nombres")
            .startAt(buscar).endAt(buscar+"\uf8ff")

        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (listaUsuarios as ArrayList<Usuario>).clear()
                for (ss in snapshot.children) {
                    val usuario: Usuario? = ss.getValue(Usuario::class.java)
                    if(!(usuario!!.uid).equals(firebaseUser)) {
                        (listaUsuarios as ArrayList<Usuario>).add(usuario)
                    }
                }
                adaptadorUsuario = AdaptadorUsuario(context!!, listaUsuarios!!)
                binding.rvUsuarios.adapter = adaptadorUsuario
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}