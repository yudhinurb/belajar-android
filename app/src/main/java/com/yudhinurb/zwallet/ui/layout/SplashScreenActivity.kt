package com.yudhinurb.zwallet.ui.layout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.ui.layout.auth.AuthActivity
import com.yudhinurb.zwallet.ui.layout.main.MainActivity
import com.yudhinurb.zwallet.utils.KEY_LOGGED_IN
import com.yudhinurb.zwallet.utils.PREFS_NAME

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        if (prefs.getBoolean(KEY_LOGGED_IN, false)) {
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        } else {
            Handler().postDelayed({
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }

        supportActionBar?.hide()


    }
}