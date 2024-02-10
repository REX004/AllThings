package com.tttrfge.deliveryapp.sesion2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.tttrfge.deliveryapp.R

class ForgotPasswordEmpty : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.forgot_password_empty, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bF1 = view.findViewById<Button>(R.id.BT_SignUp3)
        val bF2 = view.findViewById<TextView>(R.id.sign_in)
        val controller = findNavController()

        bF1.setOnClickListener {
            val intent = Intent(requireContext(), OTPVerification::class.java)
            startActivity(intent)
        }

        bF2.setOnClickListener { controller.navigate(R.id.logInEmpty) }
    }
}
