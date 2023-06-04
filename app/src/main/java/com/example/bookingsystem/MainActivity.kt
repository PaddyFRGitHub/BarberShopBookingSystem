package com.example.bookingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bookingsystem.Model.AppDatabase
import com.example.bookingsystem.Model.User
import com.example.bookingsystem.Model.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppDatabase.initialise(this)

        // For testing purposes. List all current users
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.db.userDao().getAll().forEach {
                Log.d("test", "Stored User: ${it.userName}")
            }
        }
    }

    fun loginButton(view: View) {
        val message = findViewById<TextView>(R.id.textViewMessage)
        val userName = findViewById<EditText>(R.id.editTextUserName).text.toString()
        val userPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if(userName.isEmpty() || userPassword.isEmpty())
            Toast.makeText(this,"Please insert Username and Password",Toast.LENGTH_LONG).show()
        else {
            UserManager.loginUser(userName, password = userPassword)

            val intent = Intent(this, MainActivityHome::class.java)
            startActivity(intent)
        }
    }

    fun registerButton(view: View) {

        val intent = Intent(this, MainActivityNewUser::class.java)
        startActivity(intent)
    }
}