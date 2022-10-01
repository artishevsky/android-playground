package com.artishevsky.android.playground

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artishevsky.android.playground.feature.creatures.view.allcreatures.AllCreaturesActivity
import com.artishevsky.android.playground.feature.creatures.view.creature.CreatureActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, AllCreaturesActivity::class.java)
        startActivity(intent)
    }
}