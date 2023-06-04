package com.example.bookingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

object Test {

    var t = 1
}

class MainActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)
    }

    fun bookButton(view: View) {
        val intent = Intent(this, MainActivityBook::class.java)
        startActivity(intent)
    }
    fun manageButton(view: View) {
        val intent = Intent(this, MainActivityManage::class.java)
        startActivity(intent)
    }
    fun announcementsButton(view: View) {
        val intent = Intent(this, MainActivityAnnouncements::class.java)
        startActivity(intent)
    }

}