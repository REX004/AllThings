package com.tttrfge.deliveryapp.sesion2

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

class SignUpEmpty : Fragment() {

    private lateinit var signUpButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signIn: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpButton = view.findViewById(R.id.BT_SignUp)
        emailEditText = view.findViewById(R.id.name_acc8)
        phoneEditText = view.findViewById(R.id.ET_number_phone)
        passwordEditText = view.findViewById(R.id.ET_password)
        confirmPasswordEditText = view.findViewById(R.id.ET_confirm_pass)
        signIn = view.findViewById(R.id.sign_in)

        val controller = findNavController()
        signUpButton.setOnClickListener { controller.navigate(R.id.navigation) }
        signIn.setOnClickListener { controller.navigate(R.id.logInEmpty) }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val email = emailEditText.text.toString().trim()
                val phone = phoneEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                val confirmPassword = confirmPasswordEditText.text.toString().trim()

                val isFieldsNotEmpty =
                    email.isNotEmpty() && phone.isNotEmpty() &&
                            password.isNotEmpty() && confirmPassword.isNotEmpty()

                ViewCompat.setBackgroundTintList(
                    signUpButton,
                    ContextCompat.getColorStateList(
                        requireContext(),
                        if (isFieldsNotEmpty) R.color.enabledColor else R.color.disabledColor
                    )
                )

                signUpButton.isEnabled = isFieldsNotEmpty

                val buttonColor = if (isFieldsNotEmpty) {
                    ContextCompat.getColor(requireContext(), R.color.enabledColor)
                } else {
                    ContextCompat.getColor(requireContext(), R.color.disabledColor)
                }
                signUpButton.setBackgroundColor(buttonColor)
            }
        }

        emailEditText.addTextChangedListener(textWatcher)
        phoneEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
        confirmPasswordEditText.addTextChangedListener(textWatcher)
    }
}
