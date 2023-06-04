package com.example.bookingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bookingsystem.Model.AppDatabase
import com.example.bookingsystem.Model.User
import com.example.bookingsystem.Model.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivityNewUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new_user)
    }

    fun saveNewUserButton(view: View) {
        val firstName = findViewById<EditText>(R.id.editTextFirstName).text.toString()
        val lastName  = findViewById<EditText>(R.id.editTextLastName).text.toString()
        val userName  = findViewById<EditText>(R.id.editTextNewUserName).text.toString()
        val userPassword  = findViewById<EditText>(R.id.editTextNewUserPassword).text.toString()

        val message = findViewById<TextView>(R.id.textViewMessage)

        if(firstName.isEmpty() || lastName.isEmpty() ) // First and last name are required
            message.text = "First name and last name are required!"

        else if(userName.isEmpty() || userPassword.isEmpty() ) // // User name and password are required
            message.text = "User name and Password are required!"

        else {
            UserManager.createUser(
                firstName = firstName,
                lastName = lastName,
                password = userPassword,
                isAdmin = false,
                userName = userName
            )

            message.text = "Your details has been add to the database successfully"
            findViewById<Button>(R.id. buttonSave).isEnabled = false

            // TODO: Navigate appropriately
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.db.userDao().insert(user)
        }
    }
}