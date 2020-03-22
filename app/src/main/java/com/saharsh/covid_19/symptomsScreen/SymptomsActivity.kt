package com.saharsh.covid_19.symptomsScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saharsh.covid_19.R
import kotlinx.android.synthetic.main.activity_symptoms.*

class SymptomsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms)
        init()
    }
    private fun init() {
        back.setOnClickListener { finish() }
    }
}
