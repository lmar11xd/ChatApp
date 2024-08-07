package com.lmar.chatapp.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lmar.chatapp.adaptador.AdaptadorChats
import com.lmar.chatapp.databinding.FragmentChatsBinding
import com.lmar.chatapp.entidad.Chats

class FragmentChats : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    private lateinit var mContext: Context
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var chats: ArrayList<Chats>
    private lateinit var adaptadorChats: AdaptadorChats
    private var miUid = ""

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentChatsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        miUid = firebaseAuth.uid!!

        cargarChats()
    }

    private fun cargarChats() {
        chats = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Chats")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chats.clear()
                for (ds in snapshot.children) {
                    val chatKey = "${ds.key}"
                    if(chatKey.contains(miUid)) {
                        val modeloChats = Chats()
                        modeloChats.keyChat = chatKey
                        chats.add(modeloChats)
                    }
                }

                adaptadorChats = AdaptadorChats(mContext, chats)
                binding.rvChats.adapter = adaptadorChats
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}