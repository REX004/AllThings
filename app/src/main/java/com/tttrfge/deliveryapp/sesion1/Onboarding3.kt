package com.tttrfge.deliveryapp.sesion1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.tttrfge.deliveryapp.R

class Onboarding3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.onboarding_3, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bF1 = view.findViewById<Button>(R.id.BT_SignUp)
        val bF2 = view.findViewById<TextView>(R.id.sign_in)
        val controller = findNavController()
        bF1.setOnClickListener { controller.navigate(R.id.signUpEmpty) }
        bF2.setOnClickListener { controller.navigate(R.id.logInEmpty) }
    }

}