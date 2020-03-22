package com.saharsh.covid_19.preventionScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saharsh.covid_19.R
import kotlinx.android.synthetic.main.activity_prevention.*

class PreventionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prevention)
        init()
    }
    private fun init() {
        back.setOnClickListener { finish() }
    }
}
