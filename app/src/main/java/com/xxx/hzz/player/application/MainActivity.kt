package com.xxx.hzz.player.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var audioBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        audioBtn = findViewById(R.id.audio_id)
        audioBtn.setOnClickListener(listener)
    }

    private var listener = View.OnClickListener(){
        when(it.id){
            R.id.audio_id ->{
                startActivity(Intent(this,AudioActivity::class.java))
            }
        }
    }
}