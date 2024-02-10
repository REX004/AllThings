package com.tttrfge.deliveryapp.sesion3.fragments.walletpacage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.tttrfge.deliveryapp.R

class Wallet : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }
//    @SuppressLint
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val bF1 = view?.findViewById<Button>(R.id.ad)
//        val controller = findNavController()
//        bF1?.setOnClickListener { controller.navigate(R.id.addPaymentMethod) }
//    }
}