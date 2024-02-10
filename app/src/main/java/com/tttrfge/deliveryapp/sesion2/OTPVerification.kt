package com.tttrfge.deliveryapp.sesion2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.tttrfge.deliveryapp.R

class OTPVerification : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инфлейтим макет для этого фрагмента
        return inflater.inflate(R.layout.otp_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText1 = view.findViewById<EditText>(R.id.edit_1)
        val editText2 = view.findViewById<EditText>(R.id.edit_2)
        val editText3 = view.findViewById<EditText>(R.id.edit_3)
        val editText4 = view.findViewById<EditText>(R.id.edit_4)
        val editText5 = view.findViewById<EditText>(R.id.edit_5)
        val editText6 = view.findViewById<EditText>(R.id.edit_6)

        val editTexts = arrayOf(editText1, editText2, editText3, editText4, editText5, editText6)

        for (i in 0 until editTexts.size - 1) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        editTexts[i + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        // Установите слушатель текста для последнего EditText для выполнения проверки после ввода шести цифр
        editText6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    // Вызовите метод для автоматической проверки OTP кода
                    checkOtpCode()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun checkOtpCode() {
        // Ваш код для проверки OTP кода после ввода шести цифр
        // Например, supabase.auth.verifyOtpCode("ваш_код_отп")
    }
}
