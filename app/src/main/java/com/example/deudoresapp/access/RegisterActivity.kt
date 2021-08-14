package com.example.deudoresapp.access

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.deudoresapp.DeudoresApp
import com.example.deudoresapp.DeudoresApp.Companion.userDatabase
import com.example.deudoresapp.R
import com.example.deudoresapp.data.dao.UserDao
import com.example.deudoresapp.data.entities.User
import com.example.deudoresapp.databinding.ActivityRegisterBinding
import com.example.deudoresapp.utils.emailValidator
import com.example.deudoresapp.utils.lengthString
import com.example.deudoresapp.utils.notEmptyFields
import java.sql.Types.NULL

class RegisterActivity : AppCompatActivity() {


    val PASSWORD_LENGTH = 6
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var correo: String
    private lateinit var contra: String
    private lateinit var reppassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.registerButton.setOnClickListener {
            readTextInputs()
            if (confirmInputs()) {
                if(!userExist(correo)){
                    AddToDataBase()
                    sendDataToLogin()
                } else
                    Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_SHORT).show()
            }
        }
        onChangeListener()

    }

    private fun userExist(email: String): Boolean {
        val userDao: UserDao = DeudoresApp.userDatabase.UserDao()
        if(userDao.readUser(email) != null) return true
        return false
    }


    private fun readTextInputs() {
        with(registerBinding) {
            correo = emailEditText.text.toString()
            contra = passwordEditText.text.toString()
            reppassword = confirmPasswordEditText.text.toString()
            passwordTextInputLayout.error = null
            confirmPasswordTextInputLayout.error = null
        }
    }

    private fun confirmInputs(): Boolean = notEmptyFields(correo, contra, reppassword) && validateEmail() && validateContra() && validateRepContra()

    private fun AddToDataBase() {
        val user = User(id = NULL, email = correo, password = contra)
        val userDAO : UserDao = userDatabase.UserDao()
        userDAO.createUser(user)
    }

    private fun sendDataToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("correo", correo)
        intent.putExtra("contraseña", contra)
        intent.putExtra("band", 1)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }

    private fun onChangeListener() {
        with(registerBinding){

            emailEditText.doAfterTextChanged {
                emailTextInputLayout.error = null
            }

            passwordEditText.doAfterTextChanged {
                passwordTextInputLayout.error = null
            }

            confirmPasswordEditText.doAfterTextChanged {
                confirmPasswordTextInputLayout.error = null
            }
        }
    }

    private fun validateEmail(): Boolean {
        if(!emailValidator(correo)) {
            registerBinding.emailTextInputLayout.error = getString(R.string.enter_valid_email)
            return false
        }
        return true
    }

    private fun validateContra(): Boolean {
        if(lengthString(contra, PASSWORD_LENGTH)) return true

        registerBinding.passwordTextInputLayout.error = getString(R.string.password_length)
        return lengthString(contra, PASSWORD_LENGTH)
    }

    private fun validateRepContra(): Boolean {
        if ( contra != reppassword ) {
            registerBinding.confirmPasswordTextInputLayout.error = getString(R.string.password_not_match)
            return false
        }
        return true
    }
}