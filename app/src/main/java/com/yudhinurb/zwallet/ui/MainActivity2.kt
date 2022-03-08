package com.yudhinurb.zwallet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.yudhinurb.zwallet.R

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val hello = findViewById<TextView>(R.id.helloWorld)
        hello.text = "Halo ini adalah ZWallet"
    }
}