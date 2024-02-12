package com.tttrfge.deliveryapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TestApp : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var checkEmailButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing)

        emailEditText = findViewById(R.id.editTextPhoneNumber)
        checkEmailButton = findViewById(R.id.button6)

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Не используется в данном случае
            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Не используется в данном случае
            }

            override fun afterTextChanged(s: Editable?) {
                val enteredEmail = s.toString().trim()

                if (isValidEmail(enteredEmail)) {
                    // Введенный email корректен
                    // Вы можете добавить здесь соответствующую обработку
                } else {
                    // Введенный email некорректен
                    // Вы можете добавить здесь соответствующую обработку
                }
            }
        })

        checkEmailButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString().trim()

            if (isValidEmail(enteredEmail)) {
                // Введенный email корректен
                Toast.makeText(this, "Email is valid", Toast.LENGTH_SHORT).show()
            } else {
                // Введенный email некорректен
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
