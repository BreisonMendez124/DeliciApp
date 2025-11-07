package com.example.deliciapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.deliciapp.view.products_form
import com.example.deliciapp.view.users.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lanza la actividad del formulario
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // opcional, para que no se pueda volver atrás al splash
    }
}