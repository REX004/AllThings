package com.tttrfge.deliveryapp.sesion2

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tttrfge.deliveryapp.R


class LogInEmpty : Fragment() {
    private lateinit var logInButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var forgotPassword: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.log_in_empty, container, false)
    }
    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logInButton = view.findViewById(R.id.BT_SignUp3)
        emailEditText = view.findViewById(R.id.ET_name)
        passwordEditText = view.findViewById(R.id.ET_password)
        forgotPassword = view.findViewById(R.id.forgot_pass)

        val bF1 = view.findViewById<TextView>(R.id.forgot_pass)
        val bF2 = view.findViewById<Button>(R.id.BT_SignUp3)
        val bF3 = view.findViewById<TextView>(R.id.sign_in3)
        val controller = findNavController()
        bF1.setOnClickListener { controller.navigate(R.id.forgotPasswordEmpty2) }
        bF2.setOnClickListener { controller.navigate(R.id.home3) }
        bF3.setOnClickListener { controller.navigate(R.id.signUpEmpty) }
//
//        auth.signIn(email, password, object : SupabaseAuthUserListener {
//            override fun onSuccess(result: SupabaseAuthUserResult) {
//                // User signed in successfully
//                val user: SupabaseAuthUser = result.user
//                // Handle the authenticated user
//                // Navigate to the next screen or perform necessary actions
//            }
//
//            override fun onError(error: SupabaseAuthException) {
//                // Handle authentication error
//            }
//        })

    }

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            val isFieldsNotEmpty =
                email.isNotEmpty() &&
                        password.isNotEmpty()

            ViewCompat.setBackgroundTintList(
                logInButton,
                ContextCompat.getColorStateList(
                    requireContext(),
                    if (isFieldsNotEmpty) R.color.enabledColor else R.color.disabledColor
                )
            )

            logInButton.isEnabled = isFieldsNotEmpty

            val buttonColor = if (isFieldsNotEmpty) {
                ContextCompat.getColor(requireContext(), R.color.enabledColor)
            } else {
                ContextCompat.getColor(requireContext(), R.color.disabledColor)
            }
            logInButton.setBackgroundColor(buttonColor)
        }
    }

}