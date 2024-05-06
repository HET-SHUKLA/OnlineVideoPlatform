package com.example.onlinevideoplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinevideoplatform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //View Binding
    private lateinit var b:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //init b
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)


    }
}