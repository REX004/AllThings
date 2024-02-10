package com.tttrfge.deliveryapp.sesion3.fragments.profilepac

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tttrfge.deliveryapp.R

class TransactionSuccessful1 : Fragment() {

    private var rotateClockwise: Animation? = null
    private val handler = Handler()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transaction_successful1, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rotateClockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_center)
    }

    override fun onResume() {
        super.onResume()
        val imageView = view?.findViewById<ImageView>(R.id.imageView10)


        imageView?.startAnimation(rotateClockwise)


        handler.postDelayed({
            navigateToTransactionSuccessful2()
        }, 7000)
    }

    override fun onPause() {
        super.onPause()

        val imageView = view?.findViewById<ImageView>(R.id.imageView10)

        imageView?.clearAnimation()


        handler.removeCallbacksAndMessages(null)
    }

    @SuppressLint("ResourceType")
    private fun navigateToTransactionSuccessful2() {

        val intent = Intent(requireContext(),TransactionSuccessful2::class.java)
        startActivity(intent)

        requireActivity().finish()

    }
}