package com.tttrfge.deliveryapp.sesion3.fragments.profilepac

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tttrfge.deliveryapp.R
import com.tttrfge.deliveryapp.sesion3.BottomNavigationActivity


class Profile : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bF1 = view.findViewById<CardView>(R.id.Edit_profile)
//        val bF2 = view.findViewById<RelativeLayout>(R.id.notifications)
//        val bF3 = view.findViewById<RelativeLayout>(R.id.referals)
        val controller = findNavController()
        val switch = view.findViewById<Switch>(R.id.themeSwitch)
        val activity = requireActivity() as BottomNavigationActivity
        switch.isChecked = activity.isChecked
        switch.setOnClickListener {
            activity.isChecked = !activity.isChecked;
            switch.isChecked = activity.isChecked
            if (activity.isChecked) {
               activity.setTheme(R.style.Base_Theme_DeliveryAppNight)
                // другой способ
            } else {
                activity.setTheme(R.style.Base_Theme_DeliveryAppLight)
            }
        }
        //что то хорошее

//        switch.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked)
//                requireContext().setTheme(R.style.Base_Theme_DeliveryAppNight)
//            else
//                requireContext().setTheme(R.style.Base_Theme_DeliveryAppLight
//            )
//        }
        bF1.setOnClickListener { controller.navigate(R.id.sendApackageEmpty) }
//        bF2.setOnClickListener { controller.navigate(R.id.notification) }
//        bF3.setOnClickListener { controller.navigate(R.id.transactionSuccessful1)}


        ///какой то текст
    }

}