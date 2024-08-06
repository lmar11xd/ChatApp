package com.lmar.chatapp.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.lmar.chatapp.databinding.FragmentUsuariosBinding

class FragmentUsuarios : Fragment() {

    private lateinit var binding: FragmentUsuariosBinding
    private lateinit var mContext: Context
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsuariosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}