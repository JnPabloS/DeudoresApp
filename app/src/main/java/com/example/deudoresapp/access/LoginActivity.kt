package com.example.deudoresapp.access

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.deudoresapp.DeudoresApp
import com.example.deudoresapp.MainActivity
import com.example.deudoresapp.data.local.dao.UserDao
import com.example.deudoresapp.data.local.entities.User
import com.example.deudoresapp.databinding.ActivityLoginBinding
import com.example.deudoresapp.utils.compareStrings
import com.example.deudoresapp.utils.notEmptyFields

class LoginActivity : AppCompatActivity() {

    val EMPTY = ""
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var emailInput: String
    private lateinit var contraInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loadDataFromRegister()
        loginBinding.loginButton.setOnClickListener {
            readInputs()
            if(notEmptyFields(emailInput, contraInput," ")) {
                val user = searchInDatabase(emailInput)
                if(user != null){
                    if(validateInputs(user))
                        sendDataToMain()
                    else Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                }else
                    Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()
        }
        loginBinding.registerButton.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun readInputs() {
        emailInput = loginBinding.emailEditText.text.toString()
        contraInput = loginBinding.passwordEditText.text.toString()
    }

    private fun sendDataToMain() {
        val intent = Intent(this, MainActivity::class.java)                  //Se nombra "intent" haciendo referencia a "Intent", Intent funciona Intent(de donde estoy, para donde voy)
        Toast.makeText(this, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
        startActivity(intent)                                                              //Ejecuta la sentencia
        finish()
    }

    private fun validateInputs(user: User): Boolean = compareStrings(emailInput,user.email) && compareStrings(contraInput, user.password)

    private fun loadDataFromRegister() {
        val data = intent.extras
        emailInput = data?.getString("correo").toString()
        contraInput = data?.getString("contraseña").toString()
        val band = data?.getInt("band")

        if (band == 1){
            loginBinding.emailEditText.setText(emailInput)
            loginBinding.passwordEditText.setText(contraInput)
        }
        else{
            loginBinding.emailEditText.setText(EMPTY)
            loginBinding.passwordEditText.setText(EMPTY)
        }
    }

    private fun searchInDatabase(email: String): User {
        val userDao: UserDao = DeudoresApp.userDatabase.UserDao()
        return userDao.readUser(email)
    }
}