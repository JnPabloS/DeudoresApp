package com.example.deudoresapp.utils

import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deudoresapp.databinding.ActivityLoginBinding

fun notEmptyFields(text1: String, text2: String, text3: String): Boolean {
    return text1.isNotEmpty() && text2.isNotEmpty() && text3.isNotEmpty()
}

fun compareStrings(text1: String, text2: String): Boolean {
    return text1==text2
}

fun emailValidator(text: String): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(text).matches()
}

fun lengthString(text: String, n: Int): Boolean {
    return text.length >= n
}